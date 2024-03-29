<template>
  <div>
    <div>
      <h5>
        등록
        <span class="reg-edit-date">등록일: {{ item.regDate | relativeDate }}, 수정일: {{ item.editDate | relativeDate }}</span>
      </h5>
      <b-form data-vv-scope="main-form" autocomplete="off" @submit.stop.prevent>
        <div class="row">
          <div class="col-sm-10">
            <b-form-input @change="saveProc()" v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('main-form.title')" name="title" size="sm" data-vv-as="제목"></b-form-input>
            <span v-show="!validateState('main-form.title')" class="invalid-feedback">{{ veeErrors.first('main-form.title') }}</span>
          </div>
          <div class="col-sm-2">
            <b-button variant="secondary" size="sm" @click="init()">다시읽기</b-button>
            <b-button variant="primary" size="sm" @click="listPage()">목록</b-button>
            <b-button @click="deleteProc()" type="button" size="sm" variant="danger">삭제</b-button>
          </div>
        </div>
      </b-form>
    </div>
    <div id="mynetwork" @contextmenu.prevent="$refs.ctxMenu.open"></div>
    <context-menu ref="ctxMenu">
      <ul class="list-group">
        <li class="list-group-item list-group-item-action">
          <a href="#" @click.prevent="openNodeForm()">노드추가</a>
        </li>
        <li class="list-group-item list-group-item-action">
          <a href="#" @click.prevent="openEdgeForm()">연결선 추가</a>
        </li>
        <li class="list-group-item list-group-item-action">
          <a href="#" @click.prevent="editObject()">수정</a>
        </li>
        <li class="list-group-item list-group-item-action">
          <a href="#" @click.prevent="deleteObject()">제거</a>
        </li>
        <li class="list-group-item list-group-item-action" v-show="undoHistory.length !== 0">
          <a href="#" @click.prevent="undo()">되돌리기</a>
        </li>
        <li class="list-group-item list-group-item-action" v-show="redoHistory.length !== 0">
          <a href="#" @click.prevent="redo()">앞으로돌리기</a>
        </li>
      </ul>
    </context-menu>
    <networkNode ref="nodeComponent" />
    <networkEdge ref="edgeComponent" />
  </div>
</template>

<script>
import vis from 'vis-network/dist/vis-network.js';
import networkNode from './networkNode.vue';
import networkEdge from './networkEdge.vue';
import "vis-network/dist/vis-network.css";
import contextMenu from 'vue-context-menu'

export default {
  mixins: [comFunction],
  components: {
    networkNode, networkEdge, contextMenu
  },
  data() {
    return {
      item: {
        title: "",
        content: "",
      },
      nodes: {},
      edges: {},
      network: {},
      undoHistory: [],
      redoHistory: [],
    };
  },
  methods: {
    init() {
      if (!this.$route.query.networkSeq) {
        this.item.content = "{\"nodes\": [{\"id\": \"1\", \"label\": \"복슬\", \"shape\": \"ellipse\", \"color\": \"#22ee55\" }],\"edges\": [ ]}";
        this.display();
      } else {
        VueUtil.get(`/network/item/${this.$route.query.networkSeq}`, {}, (res) => {
          this.item = res.data;
          this.display();
        });
      }
    },
    display() {
      let graphData = JSON.parse(this.item.content);
      this.nodes = new vis.DataSet(graphData.nodes);
      this.edges = new vis.DataSet(graphData.edges);
      window.addEventListener('resize', (e) => {
        this.resizeCanvas();
      });
      this.resizeCanvas();

      let edges = new vis.DataSet([]);
      let container = document.getElementById('mynetwork');
      let data = {
        nodes: this.nodes,
        edges: this.edges
      };
      let options = {
        physics: false,
        edges: {
          smooth: {
            type: 'continuous'
          }
        }
      };
      this.network = new vis.Network(container, data, options);
      let self = this;

      // 네트워크 그래프 이벤트
      this.network.on("dragEnd", (params) => this.saveProc());
      this.network.on("doubleClick", (params) => {
        this.editObject();
      });
    },
    listPage() {
      this.$router.push({ name: "networkList", query: this.$route.query });
    },
    deleteProc() {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        if (!this.item.networkSeq) {
          this.listPage();
          return;
        }
        VueUtil.delete(`/network/item/${this.item.networkSeq}`, this.item, (res) => {
          this.listPage();
        });
      });
    },
    editObject() {
      let nodeId = this.getSelectNodeId();
      if (nodeId) {
        this.openNodeForm(this.nodes.get(nodeId));
        return;
      }
      let edgeId = this.getSelectEdgeId();
      if (edgeId) {
        this.openEdgeForm(this.edges.get(edgeId));
        return;
      }
      Swal.fire('안내', "뭐라도 선택해라.", 'info');
    },
    openNodeForm(node) {
      this.$refs["nodeComponent"].show(node, this.getSelectNodeId() != null);
    },
    openEdgeForm(edge) {
      this.$refs['edgeComponent'].show(this.nodes.get(), edge, this.getSelectNodeId());
    },
    // 선택한 node 반환
    getSelectNodeId() {
      let selectionList = this.network.getSelection();
      if (selectionList.nodes.length > 0) {
        return selectionList.nodes[0];
      }
      return null;
    },
    // 선택한 edge 반환.
    getSelectEdgeId() {
      let selectionList = this.network.getSelection();
      // edge만 선택 시 반환. 즉 노드 선택을 하여 edge가 선택된 경우는 null 반환
      if (selectionList.nodes.length > 0) {
        return null;
      }
      if (selectionList.edges.length > 0) {
        return selectionList.edges[0];
      }
      return null;
    },
    resizeCanvas() {
      this.canvasHeight = $(window).height() - 155;
      $("#mynetwork").height(this.canvasHeight);
    },
    getJson() {
      let position = this.network.getPositions()
      let ndoes = this.nodes.get();
      ndoes.forEach((v) => {
        let p = position[v.id];
        v["x"] = p.x;
        v["y"] = p.y;
      });
      let data = { nodes: ndoes, edges: this.edges.get() };
      let exportValue = JSON.stringify(data, undefined, 2);
      return exportValue;
    },
    // 신규 노드 등록
    addNode(node, edgeLable) {
      this.undoHistory.push(this.getJson());
      this.redoHistory = [];
      this.nodes.add(node);

      let selectNodeId = this.getSelectNodeId();
      if (selectNodeId) {
        this.edges.add({
          from: selectNodeId,
          to: node.id,
          label: edgeLable,
          color: { color: '#777777', highlight: '#777777' }
        });
      }
      this.saveProc();
    },
    editNode(node) {
      this.undoHistory.push(this.getJson());
      this.redoHistory = [];

      this.nodes.update(node);
      this.saveProc();
    },
    // 신규 엣지 등록
    addEdge(edge) {
      this.undoHistory.push(this.getJson());
      this.redoHistory = [];

      this.edges.add(edge);
      this.saveProc();
    },
    editEdge(edge) {
      this.undoHistory.push(this.getJson());
      this.redoHistory = [];

      this.edges.update(edge);
      this.saveProc();
    },
    // 노드 또는 엣지 삭제
    deleteObject() {
      this.undoHistory.push(this.getJson());
      this.redoHistory = [];

      let selectionList = this.network.getSelection();
      selectionList.nodes.forEach((id) => {
        this.nodes.remove({ id: id });
      });
      selectionList.edges.forEach((id) => {
        this.edges.remove({ id: id });
      });
      this.saveProc();
    },
    redo() {
      console.log("앞으로돌리기");
      this.item.content = this.redoHistory.pop();
      this.undoHistory.push(this.getJson());
      this.display();
      this.saveProc();
    },
    undo() {
      this.item.content = this.undoHistory.pop();
      this.redoHistory.push(this.getJson());
      this.display();
      this.saveProc();
    },
    addProc() {
      VueUtil.post("/network/item", this.item, (res) => {
        this.item = res.data;
      }, { wait: false });
    },
    editProc() {
      VueUtil.put("/network/item", this.item, (res) => {
        this.item = res.data;
      }, { wait: false });
    },
    // 현 상태 서버 저장
    saveProc() {
      if (CommonUtil.isEmpty(this.item.title)) {
        return;
      }
      this.item.content = this.getJson();
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }
        if (!this.item.networkSeq) {
          this.addProc();
        } else {
          this.editProc();
        }
      });
    },
  },
  mounted() {
    this.init();
  },
};
</script>


<style scoped>
  #mynetwork {
    width: 100%;
    height: 100%;
    background-color: #f0f0f5;
    margin-top:10px;
  }
</style>

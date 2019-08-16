<template>
  <div>
    <div>
      <h5>글 등록</h5>
      <div class="row">
        <div class="col-sm-10">
          <b-form-input @change="changeProc()" v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('item.title')" name="item.title" size="sm" data-vv-as="제목"></b-form-input>
          <span v-show="!validateState('item.title')" class="invalid-feedback">{{ veeErrors.first('item.title') }}</span>
        </div>
        <div class="col-sm-2">
          <b-button variant="primary" size="sm" @click="listPage()">목록</b-button>
          <b-button @click="deleteProc()" type="button" size="sm" variant="danger">삭제</b-button>
        </div>
      </div>
    </div>
    <div id="mynetwork" @contextmenu.prevent="$refs.menu.open">aaa</div>
    <vue-context ref="menu">
      <li>
        <a href="#" @click.prevent="addNodeForm()">노드추가</a>
      </li>
      <li>
        <a href="#" @click.prevent="addEdgeForm()">연결선 추가</a>
      </li>
      <li>
        <a href="#" @click.prevent="action()">수정</a>
      </li>
      <li>
        <a href="#" @click.prevent="action()">제거</a>
      </li>
      <li>----------------------------</li>
      <li>
        <a href="#" @click.prevent="undo()">되돌리기</a>
      </li>
      <li>
        <a href="#" @click.prevent="redo()">앞으로돌리기</a>
      </li>
    </vue-context>

    <b-modal ref="nodeForm" title="노드 추가" @ok="addNodeProc()">
      <div>
        <b-form>
          <b-form-group label="레이블" label-for="input-label">
            <b-form-input id="input-label"></b-form-input>
          </b-form-group>
          <b-form-group label="모양" id="input-shape">
            <b-form-radio-group>
              <b-form-radio value="1">타원</b-form-radio>
              <b-form-radio value="2">사각형</b-form-radio>
              <b-form-radio value="3">원</b-form-radio>
            </b-form-radio-group>
          </b-form-group>
          <b-form-group label="색" id="input-color">
            <b-form-radio-group>
              <b-form-radio value="1">
                <span class="color_label" style="background: #ffffcc;" />
              </b-form-radio>
              <b-form-radio value="#ffffff">
                <span class="color_label" style="background: #ffffff;" />
              </b-form-radio>
              <b-form-radio value="#ffff66">
                <span class="color_label" style="background: #ffff66;" />
              </b-form-radio>
              <b-form-radio value="#ccff66">
                <span class="color_label" style="background: #ccff66;" />
              </b-form-radio>
              <b-form-radio value="#99ccff">
                <span class="color_label" style="background: #99ccff;" />
              </b-form-radio>
              <b-form-radio value="#ff99ff">
                <span class="color_label" style="background: #ff99ff;" />
              </b-form-radio>
              <b-form-radio value="#eeeeee">
                <span class="color_label" style="background: #eeeeee;" />
              </b-form-radio>
            </b-form-radio-group>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>

    <b-modal ref="edgeForm" title="연결선 추가" @ok="addNodeProc()">
      <div>
        <b-form>
          <b-form-group label="시작" label-for="input-label">
            <b-form-select size="sm">
              <option :value="null">--선택--</option>
              <option value="a">1번</option>
              <option value="b">2번</option>
            </b-form-select>
          </b-form-group>
          <b-form-group label="끝" label-for="input-label">
            <b-form-select size="sm">
              <option :value="null">--선택--</option>
              <option value="a">1번</option>
              <option value="b">2번</option>
            </b-form-select>
          </b-form-group>
          <b-form-group label="선 형태" id="input-shape">
            <b-form-radio-group>
              <b-form-radio value="1">실선</b-form-radio>
              <b-form-radio value="2">점선</b-form-radio>
            </b-form-radio-group>
          </b-form-group>
          <b-form-group label="색" id="input-color">
            <b-form-radio-group>
              <b-form-radio value="#777777">
                <span class="color_label" style="background: #777777;" />
              </b-form-radio>
              <b-form-radio value="#006699">
                <span class="color_label" style="background: #006699;" />
              </b-form-radio>
              <b-form-radio value="#00ff00">
                <span class="color_label" style="background: #00ff00;" />
              </b-form-radio>
              <b-form-radio value="#aa44aa">
                <span class="color_label" style="background: #aa44aa;" />
              </b-form-radio>
              <b-form-radio value="#cc9900">
                <span class="color_label" style="background: #cc9900;" />
              </b-form-radio>
              <b-form-radio value="#cc0066">
                <span class="color_label" style="background: #cc0066;" />
              </b-form-radio>
              <b-form-radio value="#3333ff">
                <span class="color_label" style="background: #3333ff;" />
              </b-form-radio>
            </b-form-radio-group>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>
  </div>
</template>

<script>
import comFunction from "../commonFunction.js";
import VueUtil from '../../utils/vue-util.js'
import CommonUtil from '../../utils/common-util.js'
import "vis/dist/vis.css";
import vis from 'vis/dist/vis.js';
import { VueContext } from 'vue-context';

export default {
  mixins: [comFunction],
  components: {
    VueContext
  },
  data() {
    return {
      item: {
        title: "",
        content: "",
      },
      nodes: {},
      edges: {},
      network: {}
    };
  },
  methods: {
    init() {
      if (!this.$route.query.networkSeq) {
        this.item.content = "{\"nodes\": [{\"id\": \"1\", \"label\": \"복슬\", \"shape\": \"ellipse\", \"color\": \"#22ee55\" }],\"edges\": [ ]}";
        let graphData = JSON.parse(this.item.content);
        this.display(graphData);
      } else {
        VueUtil.get(`/network/item/${this.$route.query.networkSeq}`, {}, (res) => {
          this.item = res.data;
          let graphData = JSON.parse(this.item.content);
          this.display(graphData);
        });
      }
    },
    display(graphData) {
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
      this.network.on("dragEnd", (params) => this.changeProc());
      this.network.on("doubleClick", (params) => {
        console.log('params :', params);
        this.$refs['edgeForm'].show();
      });
    },
    listPage() {
      this.$router.push({ name: "networkList" });
    },
    deleteProc() {
      console.log("delete");
    },
    addNodeForm() {
      this.$refs['nodeForm'].show()
      let node = this.getSelectNodeId();
      console.log('node :', node);
    },
    addNodeProc() {
      console.log('addNodeProc');
    },
    addEdgeForm() {
      this.$refs['edgeForm'].show()
    },
    addEdgeProc() {
      console.log('addEdgeProc');
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
    redo() {
      console.log("앞으로돌리기");
    },
    undo() {
      console.log("되돌리기");
    },
    changeProc() {
      console.log("changeProc");
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
      console.log("#####################");
    },
    addProc() {
      VueUtil.post("/network/item", this.item, (res) => {
        this.item = res.data;
      });
    },
    editProc() {
      VueUtil.put("/network/item", this.item, (res) => {
        this.item = res.data;
      });
    }
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
  .color_label {
    border: 1px solid #CCC;
    padding: 10px;
    display: inline-block;
  }
</style>

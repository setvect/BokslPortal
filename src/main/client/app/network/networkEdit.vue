<template>
  <div>
    <div>
      <h5>글 등록</h5>
      <div class="row">
        <div class="col-sm-10">
          <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('item.title')" name="item.title" size="sm" data-vv-as="제목"></b-form-input>
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
        <a href="#" @click.prevent="action()">되돌리기</a>
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
        title: "삼체2",
      },
      nodes: {
      }
    };
  },
  methods: {
    listPage() {
      this.$router.push({ name: "networkList" });
    },
    deleteProc() {
      console.log("delete");
    },
    addNodeForm() {
      this.$refs['nodeForm'].show()
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
    resizeCanvas() {
      this.canvasHeight = $(window).height() - 155;
      $("#mynetwork").height(this.canvasHeight);
    },
  },
  mounted() {
    window.addEventListener('resize', (e) => {
      this.resizeCanvas();
    });
    this.resizeCanvas();
    console.log('vis :', vis);

    // create an array with nodes
    var nodes = new vis.DataSet([
      { id: 1, label: 'Node 1' },
      { id: 2, label: 'Node 2' },
      { id: 3, label: 'Node 3' },
      { id: 4, label: 'Node 4' },
      { id: 5, label: 'Node 5' }
    ]);

    // create an array with edges
    var edges = new vis.DataSet([
      { from: 1, to: 3 },
      { from: 1, to: 2 },
      { from: 2, to: 4 },
      { from: 2, to: 5 },
      { from: 3, to: 3 }
    ]);

    // create a network
    var container = document.getElementById('mynetwork');
    var data = {
      nodes: nodes,
      edges: edges
    };
    var options = {};
    var network = new vis.Network(container, data, options);
  }
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

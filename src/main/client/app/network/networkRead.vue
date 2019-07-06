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
    <div id="mynetwork" class="_canvas">aaa</div>
  </div>
</template>

<script>
import comFunction from "../commonFunction.js";
import "vis/dist/vis.css";
import vis from 'vis/dist/vis.js';

export default {
  mixins: [comFunction],
  data() {
    return {
      item: {
        title: "삼체2",
      },
      canvasHeight: 0
    };
  },
  methods: {
    listPage() {
      this.$router.push({ name: "networkList" });
    },
    deleteProc() {
      console.log("delete");
    },
    resizeCanvas() {
      this.canvasHeight = $(window).height() - 155;
      $("#mynetwork").height(this.canvasHeight);
    }
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
</style>

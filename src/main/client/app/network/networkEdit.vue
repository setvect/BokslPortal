<template>
  <div>
    <div>
      <h5>글 등록</h5>
      <b-form data-vv-scope="form-1" autocomplete="off">
        <div class="row">
          <div class="col-sm-10">
            <b-form-input v-model="title" v-validate="{ required: true, max: 100 }" :state="validateState('form-1.title')" name="title" size="sm" data-vv-as="제목"></b-form-input>
            <span v-show="!validateState('form-1.title')" class="invalid-feedback">{{ veeErrors.first('form-1.title') }}</span>
          </div>
          <div class="col-sm-2">
            <b-button variant="primary" size="sm" @click="listPage()">목록</b-button>
            <b-button @click="deleteProc()" type="button" size="sm" variant="danger">삭제</b-button>
          </div>
        </div>
      </b-form>
    </div>
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
  data() {
    return {
      title: 'a '
    };
  },
  methods: {
  },
  mounted() {
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

<template>
  <b-modal ref="categoryForm" title="카테고리 분류" @ok.prevent="applyCategory">
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <Tree ref="tree" :data="categoryTree" style="margin-bottom:10px;" draggable>
          <div slot-scope="{data, store}">
            <template>
              <strong v-if="data.children && data.children.length" @click="store.toggleOpen(data)">{{data.open ? '-' : '+'}}&nbsp;</strong>
              <span v-if="data.data">
                <b-form-input
                  :name="data._id"
                  type="text"
                  v-model="data.data.name"
                  :state="validateState(data._id)"
                  v-validate="{ required: true, max: 30}"
                  data-vv-as="이름 "
                  size="sm"
                  style="margin-right:30px; display:inline;width:auto;"
                />
                <b-button v-if="categoryTree.length > 1" @click="store.deleteNode(data)" size="sm" type="button" variant="info" style="float:right;">삭제</b-button>
                <span v-show="!validateState(data._id)" class="invalid-feedback">{{ veeErrors.first(data._id) }}</span>
              </span>
            </template>
          </div>
        </Tree>
        <b-button @click="addChild" size="sm" type="button" variant="primary">추가</b-button>
      </b-form>
    </div>
  </b-modal>
</template>

<script>
import Vue from "vue";
import noteCommon from "./mixin-note.js";
import store from "../../store/index.js";
import { DraggableTree } from 'vue-draggable-nested-tree';
import EventBus from "../../utils/event-bus.js";
import '../../utils/vue-common.js'

export default {
  mixins: [comFunction, noteCommon],
  components: { Tree: DraggableTree },
  data() {
    return {
      categoryTree: [],
    };
  },
  methods: {
    open() {
      store.dispatch('note/loadTree').then(() => {
        this.categoryTree = store.state.note.categoryTree.children;
        this.$refs['categoryForm'].show();
      });
    },
    // add child to tree2
    addChild() {
      this.categoryTree.push({ data: { "categorySeq": 0, "name": "", "regDate": 1567499071022, "deleteF": false, "root": false }, children: [] });
    },
    applyCategory() {
      this.$validator.validate().then((result) => {
        console.log('result :', result);
        if (!result) {
          return;
        }

        let pureCategory = this.$refs["tree"].pure(this.$refs["tree"].rootData, true).children;
        let childrenCategory = [];
        let rootCategory = $.extend(true, {}, store.state.note.categoryTree.data);
        this.processCategory(pureCategory, rootCategory);

        let json = JSON.stringify(rootCategory);
        VueUtil.put("/note/category", json, (res) => {
          this.$refs['categoryForm'].hide();
          EventBus.$emit("reloadNoteCategory");
        }, { "call-type": "json" });
      });
    },
    // NoteCategoryVo 형태로 값을 구성함. 재귀호출
    processCategory(categoryList, accCategory) {
      accCategory.children = [];
      categoryList.forEach((category) => {
        let data = category.data;
        accCategory.children.push(data);
        this.processCategory(category.children, data);
      });
    }
  },
  mounted() {
  }
};
</script>

<style lang="scss">
.tree-node-inner {
  padding: 5px;
  border: 1px solid #ccc;
  cursor: pointer;
}
.draggable-placeholder-inner {
  border: 1px dashed #0088f8;
  box-sizing: border-box;
  background: rgba(0, 136, 249, 0.09);
  color: #0088f9;
  text-align: center;
  padding: 0;
  display: flex;
  align-items: center;
}
</style>

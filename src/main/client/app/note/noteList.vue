<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.field" size="sm">
          <option value="name">이름</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info">만들기</b-button>
        <b-button @click="categoryForm()" size="sm" type="button" variant="outline-primary" style="float:right">카테고리</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="listData">
      <template slot="index" slot-scope="data" style>{{ data.index + 1 }}</template>
      <template slot="title" slot-scope="data">
        <b-link @click="readPage(data.item.noteSeq)">{{ data.item.title }}</b-link>
      </template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.noteSeq)">수정</b-link>
        <b-link @click="deleteProc(data.noteSeq)">삭제</b-link>
      </template>
    </b-table>
    <b-pagination v-model="searchData.currentPage" :total-rows="page.total" :per-page="page.perPage" @change="changePage" limit="10" align="center" />
    <b-modal ref="categoryForm" title="카테고리 분류" @shown="shownAddEvent">
      <div>
        <b-form autocomplete="off" @submit.stop.prevent>
          <Tree :data="categoryTree" draggable>
            <div slot-scope="{data, store}">
              <template>
                <b v-if="data.children && data.children.length" @click="store.toggleOpen(data)">{{data.open ? '-' : '+'}}&nbsp;</b>
                <span>{{data.data.name}}</span>
              </template>
            </div>
          </Tree>
          <b-button @click="addChild" size="sm" type="button" variant="primary">Add child</b-button>
        </b-form>
      </div>
    </b-modal>
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import store from "../../store/index.js";
import { DraggableTree } from 'vue-draggable-nested-tree';

export default {
  mixins: [noteCommon],
  components: { Tree: DraggableTree },
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "title", label: "제목" },
        { key: "function", label: "기능", class: 'function-col' }
      ],
      listData: [
        {
          noteSeq: 1,
          title: "제목입니다1"
        },
        {
          noteSeq: 2,
          title: "제목입니다2"
        },
        {
          noteSeq: 3,
          title: "제목입니다31"
        }
      ],
      searchData: {
        field: "name",
        word: null,
        currentPage: 1
      },
      page: {
        total: 300,
        perPage: 10
      },
      categoryTree: [],
      tree2data: [
        { text: 'node 1' },
        { text: 'node 2' },
        { text: 'node 3' },
        { text: 'node 4' },
      ],
    };
  },
  watch: {
    '$route.query.categorySeq'() {
      console.log('this.$route.query.categorySeq :', this.$route.query.categorySeq);
    }
  },
  methods: {
    addPage() {
      this.$router.push({ name: "noteAdd" });
    },
    readPage() {
      this.$router.push({ name: "noteRead" });
    },
    changePage(page) {
      console.log("page :", page);
    },
    categoryForm() {
      console.log("categoryForm");
      this.$refs['categoryForm'].show();
    },
    // 입력 창 오픈시
    shownAddEvent(event) {
      console.log("shownAddEvent");
    },
    // add child to tree2
    addChild() {

      this.categoryTree.push({ data: { "categorySeq": 10, "name": "카테고리122", "regDate": 1567499071022, "deleteF": false, "root": false }, children: [] });


      console.log('this.categoryTree[0] :', this.categoryTree[0]);
      // this.categoryTree[0].children.push({ text: 'child' })
    },
  },
  mounted() {
    store.dispatch('note/loadTree').then(() => {
      this.categoryTree = store.state.note.categoryTree.children;
    });
  }
};
</script>

<style lang="scss">
.index-col {
  width: 50px;
}
.function-col {
  width: 140px;
}
.he-tree {
  border: 1px solid #ccc;
  padding: 20px;
}
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

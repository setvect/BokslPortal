<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchParam.field" size="sm">
          <option value="title">제목</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input @keypress.13="search()" v-model="searchParam.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info">만들기</b-button>
        <b-button @click="categoryForm()" size="sm" type="button" variant="outline-primary" style="float:right">카테고리</b-button>
      </b-form>
    </div>
    <div>
      <ol class="breadcrumb">
        <li v-for="category in categoryPath" :key="category.categorySeq" class="breadcrumb-item" :class="{'active': category.categorySeq == searchParam.categorySeq}">{{category.name}}</li>
      </ol>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list">
      <template slot="index" slot-scope="data" style>{{ data.index + 1 }}</template>
      <template slot="title" slot-scope="data">
        <b-link @click="readPage(data.item.noteSeq)">{{ data.item.title }}{{data.item.attach.length === 0 ? "" : " [" + data.item.attach.length + "]" }}</b-link>
      </template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.item.noteSeq)">수정</b-link>
        <b-link @click="deleteProc(data.item.noteSeq)">삭제</b-link>
      </template>
    </b-table>
    <b-pagination v-model="searchParam.currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
    <note-category ref="categoryCmp" />
    <div style="padding:5px 0"></div>
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import noteCategoryComponent from "./noteCategory.vue";
import store from "../../store/index.js";
import { DraggableTree } from 'vue-draggable-nested-tree';

export default {
  mixins: [noteCommon],
  components: {
    noteCategory: noteCategoryComponent
  },
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "category.name", label: "분류", class: 'category-col' },
        { key: "title", label: "제목" },
        { key: "function", label: "기능", class: 'function-col' }
      ],
      searchParam: {
        field: "title",
        word: null,
        startCursor: 0,
        currentPage: 1,
        categorySeq: 0,
      },
      page: {
        total: 0,
        list: []
      },
      categoryPath: []
    };
  },
  watch: {
    '$route.query.categorySeq'() {
      this.searchParam.categorySeq = this.$route.query.categorySeq;
      this.getCategoryPath();
      this.searchParam.word = "";
      this.searchParam.field = "title";
      this.search();
    }
  },
  methods: {
    listProc() {
      VueUtil.get("/note/page", this.searchParam, (res) => {
        this.page = res.data;
      });
    },
    search() {
      this.page.startCursor = 0;
      this.listProc();
    },
    addPage() {
      delete this.$route.query.noteSeq;
      this.$router.push({ name: "noteAdd", query: this.$route.query });
    },
    readPage(noteSeq) {
      this.$route.query.noteSeq = noteSeq;
      this.$router.push({ name: "noteRead", query: this.$route.query });
    },
    changePage(page) {
      this.searchParam.startCursor = this.page.returnCount * (page - 1)
      this.listProc();
    },
    categoryForm() {
      this.$refs['categoryCmp'].open();
    },
    getCategoryPath() {
      if (!this.searchParam.categorySeq) {
        this.categoryPath = [{ name: "홈" }];
        return;
      }
      VueUtil.get(`/note/category-path/${this.searchParam.categorySeq}`, {}, (res) => {
        this.categoryPath = res.data.slice(1);
      });
    },

  },
  mounted() {
    this.searchParam.categorySeq = parseInt(this.$route.query.categorySeq);
    this.getCategoryPath();
    this.listProc();
  }
};
</script>

<style lang="scss">
.index-col {
  width: 50px;
}
.category-col {
  width: 150px;
}
.function-col {
  width: 140px;
}
</style>

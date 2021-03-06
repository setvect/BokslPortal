<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select @change="changeCategory()" v-model="currentCategorySeq" name="categorySeq" size="sm">
          <option :value="0">==전체==</option>
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>

        <b-form-select v-model="$route.query.field" size="sm">
          <option value="title">제목</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input @keypress.13.prevent="search()" v-model="$route.query.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage(false)" size="sm" type="button" variant="info" style="margin-left:30px;">만들기</b-button>
        <b-button @click="addPage(true)" size="sm" type="button" variant="info" style="margin-left:10px;">만들기(마크다운)</b-button>
        <b-button @click="categoryForm()" size="sm" type="button" variant="outline-primary" style="float:right">카테고리</b-button>
      </b-form>
    </div>
    <div>
      <ol class="breadcrumb">
        <li v-for="category in categoryPath" :key="category.categorySeq" class="breadcrumb-item" :class="{'active': category.categorySeq == $route.query.categorySeq}">{{category.name}}</li>
      </ol>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list">
      <template slot="index" slot-scope="data" style>{{ data.index | indexSeq(page) }}</template>
      <template slot="title" slot-scope="data">
        <b-link @click="readPage(data.item.noteSeq)">{{ data.item.title }}{{data.item.attach.length === 0 ? "" : " [" + data.item.attach.length + "]" }}</b-link>
      </template>
      <template slot="markdown" slot-scope="data">{{data.item.markdownF ? "예" : "아니오"}}</template>
      <template slot="regDate" slot-scope="data">{{data.item.regDate | relativeDate}}</template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.item.noteSeq)">수정</b-link>
        <b-link @click="deleteProc(data.item.noteSeq)">삭제</b-link>
      </template>
    </b-table>
    <b-pagination v-model="currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
    <note-category ref="categoryCmp" />
    <div style="padding:5px 0"></div>
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import noteCategoryComponent from "./noteCategory.vue";
import store from "../../store/index.js";
import { DraggableTree } from 'vue-draggable-nested-tree';
import '../../utils/vue-common.js'

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
        { key: "markdown", label: "마크다운" },
        { key: "regDate", label: "날짜", class: 'date-col' },
        { key: "function", label: "기능", class: 'function-col' }
      ],
      page: {
        totalCount: 0,
        list: []
      },
      categoryPath: [],
      currentPage: 1,
      isSearch: false,
      currentCategorySeq: 0
    };
  },
  methods: {
    listProc() {
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.word)
      VueUtil.get("/note/page", this.$route.query, (res) => {
        this.page = res.data;
        this.$nextTick(() => {
          this.currentPage = this.$route.query.currentPage;
        });
      });
    },
    search() {
      let param = $.extend({}, this.$route.query);
      this.$route.query.startCursor = -1;
      param["startCursor"] = 0;
      this.$router.push({ name: "noteList", query: param }).catch(err => { });
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
    },
    addPage(markdown) {
      delete this.$route.query.noteSeq;
      this.$router.push({ name: "noteAdd", query: {...this.$route.query, markdown} });
    },
    readPage(noteSeq) {
      this.$route.query.noteSeq = noteSeq;
      this.$router.push({ name: "noteRead", query: this.$route.query });
    },
    changePage(page) {
      let param = $.extend({}, this.$route.query);
      param["startCursor"] = this.page.returnCount * (page - 1);
      param["currentPage"] = page;
      this.$router.push({ name: "noteList", query: param }).catch(err => { });
    },
    categoryForm() {
      this.$refs['categoryCmp'].open();
    },
    getCategoryPath() {
      if (!this.$route.query.categorySeq || this.$route.query.categorySeq == "0") {
        this.categoryPath = [{ name: "전체" }];
        return;
      }
      VueUtil.get(`/note/category-path/${this.$route.query.categorySeq}`, {}, (res) => {
        this.categoryPath = res.data.slice(1);
      });
    },
    // 카테고리 변경
    changeCategory() {
      let param = $.extend({}, this.$route.query);
      param["startCursor"] = 0;
      param["currentPage"] = 1;
      param["categorySeq"] = this.currentCategorySeq;
      this.$router.push({ name: "noteList", query: param }).catch(err => { });
      this.getCategoryPath();
    }
  },
  mounted() {
    this.$route.query.categorySeq = parseInt(this.$route.query.categorySeq || 0);
    this.currentCategorySeq = this.$route.query.categorySeq;
    if (!this.$route.query.field) {
      this.$route.query.field = "title";
    }
    this.getCategoryPath();
    this.listProc();
    this.loadCategory();
  },
  beforeRouteUpdate(to, from, next) {
    next();
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
.date-col {
  width: 140px;
}
</style>

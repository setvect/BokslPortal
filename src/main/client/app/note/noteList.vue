<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="$route.query.field" size="sm">
          <option value="title">제목</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input @keypress.13="search()" v-model="$route.query.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info" style="margin-left:30px;">만들기</b-button>
        <b-button @click="categoryForm()" size="sm" type="button" variant="outline-primary" style="float:right">카테고리</b-button>
      </b-form>
    </div>
    <div>
      <ol class="breadcrumb">
        <li v-for="category in categoryPath" :key="category.categorySeq" class="breadcrumb-item" :class="{'active': category.categorySeq == $route.query.categorySeq}">{{category.name}}</li>
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
      page: {
        totalCount: 0,
        list: []
      },
      categoryPath: [],
      currentPage: 1,
    };
  },
  watch: {
    '$route.query.categorySeq'() {
      this.$route.query.categorySeq = this.$route.query.categorySeq;
      this.getCategoryPath();
      this.$route.query.word = "";
      this.$route.query.field = "title";
      this.search();
    }
  },
  computed: {
    isSearch() {
      return this.$route.query.word;
    }
  },
  methods: {
    listProc() {
      let currentPage = this.$route.query.currentPage;
      VueUtil.get("/note/page", this.$route.query, (res) => {
        this.page = res.data;
        this.$nextTick(() => {
          this.currentPage = currentPage;
        });
      });
    },
    search() {
      this.$route.query.startCursor = 0;
      this.listProc();
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
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
      this.$route.query.startCursor = this.page.returnCount * (page - 1)
      this.$route.query.currentPage = page;
      this.listProc();
    },
    categoryForm() {
      this.$refs['categoryCmp'].open();
    },
    getCategoryPath() {
      if (!this.$route.query.categorySeq) {
        this.categoryPath = [{ name: "홈" }];
        return;
      }
      VueUtil.get(`/note/category-path/${this.$route.query.categorySeq}`, {}, (res) => {
        this.categoryPath = res.data.slice(1);
      });
    },

  },
  mounted() {
    this.$route.query.categorySeq = parseInt(this.$route.query.categorySeq);
    if (!this.$route.query.field) {
      this.$route.query.field = "title";
    }
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

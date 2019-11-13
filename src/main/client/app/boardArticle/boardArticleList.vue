<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="$route.query.field" size="sm">
          <option value="title">제목</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input @keypress.13.prevent="search()" v-model="$route.query.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info" style="margin-left:30px;">만들기</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list">
      <template slot="index" slot-scope="data">{{ data.index | indexSeq(page) }}</template>
      <template slot="title" slot-scope="data">
        <b-link @click="readPage(data.item.boardArticleSeq)">{{ data.item.title }}{{data.item.attach.length === 0 ? "" : " [" + data.item.attach.length + "]" }}{{data.item.encryptF ? "(암호화)" : ""}}</b-link>
      </template>
      <template slot="regDate" slot-scope="data">{{data.item.regDate | relativeDate}}</template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.item.boardArticleSeq)">수정</b-link>
        <b-link @click="deleteProc(data.item.boardArticleSeq)">삭제</b-link>
      </template>
    </b-table>
    <b-pagination v-model="currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
  </div>
</template>

<script>
import boardCommon from "./mixin-boardArticle.js";
import boardArticleEncryptComponent from "./boardArticleEncrypt.vue";

export default {
  mixins: [boardCommon],
  components: {
    boardArticleEncrypt: boardArticleEncryptComponent
  },
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "title", label: "제목" },
        { key: "regDate", label: "날짜", class: 'date-col' },
        { key: "function", label: "기능", class: 'function-col' }
      ],
      page: {
        totalCount: 0,
        list: []
      },
      currentPage: 1,
      isSearch: false
    };
  },
  computed: {
  },
  watch: {
    '$route.query.boardCode'() {
      this.$route.query.field = "title";
      this.search();
    }
  },
  methods: {
    listProc() {
      let currentPage = this.$route.query.currentPage;
      VueUtil.get("/board-article/page", this.$route.query, (res) => {
        this.page = res.data;
        this.$nextTick(() => {
          this.currentPage = currentPage;
        });
      });
    },
    search() {
      this.$route.query.startCursor = 0;
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.word)
      this.listProc();
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
    },
    addPage() {
      delete this.$route.query.boardArticleSeq;
      this.$router.push({ name: "boardArticleAdd", query: this.$route.query });
    },
    readPage(boardArticleSeq) {
      this.$route.query.boardArticleSeq = boardArticleSeq;
      this.$router.push({ name: "boardArticleRead", query: this.$route.query });
    },
    changePage(page) {
      let param = $.extend({}, this.$route.query);
      param["startCursor"] = this.page.returnCount * (page - 1);
      param["currentPage"] = page;
      this.$router.push({ name: "boardArticleList", query: param }).catch(err => {});
    }
  },
  mounted() {
    if (!this.$route.query.field) {
      this.$route.query.field = "title";
    }
    this.loadBoardManager();
    this.listProc();
  },
  beforeRouteUpdate(to, from, next) {
    next();
    this.listProc();
  }
};
</script>

<style >
  .index-col{
    width: 50px;
  }
  .function-col{
    width: 140px;
  }
  .date-col {
    width: 140px;
  }
</style>

<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="$route.query.classifyC" size="sm">
          <option :value="null">--전체--</option>
          <option v-for="code in classifyList" :key="code.minorCode" :value="code.minorCode">{{code.codeValue}}</option>
        </b-form-select>
        <b-input @keypress.13.prevent="search()" v-model="$route.query.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage()" variant="info" size="sm" style="margin-left:30px;">만들기</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list">
      <template slot="index" slot-scope="data">{{ data.index | indexSeq(page) }}</template>
      <template slot="classifyC" slot-scope="data">{{data.item.classifyC}}</template>
      <template slot="problem" slot-scope="data">
        <b-link @click="readPage(data.item.knowledgeSeq)">{{clearHtml(data.item.problem)}}</b-link>
      </template>
      <template slot="solution" slot-scope="data">
        <b-link @click="readPage(data.item.knowledgeSeq)">{{clearHtml(data.item.solution)}}</b-link>
      </template>
      <template slot="file" slot-scope="data">{{data.item.attach.length}}</template>
      <template slot="regDate" slot-scope="data">{{data.item.regDate | relativeDate}}</template>
    </b-table>
    <b-pagination v-model="currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
  </div>
</template>

<script>
import '../../utils/vue-common.js'
import knowledgeCommon from "./mixin-knowledge.js";

export default {
  mixins: [comFunction, knowledgeCommon],
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "classifyC", label: "분류", class: 'classify-col' },
        { key: "problem", label: "질문", class: 'content-col' },
        { key: "solution", label: "답변", class: 'content-col' },
        { key: "file", label: "파일", class: 'file-col' },
        { key: "regDate", label: "날짜", class: 'date-col' }
      ],
      page: {
        total: 0,
        list: []
      },
      categoryList: [],
      currentPage: 1,
      isSearch: false
    };
  },
  methods: {
    listProc() {
      let currentPage = this.$route.query.currentPage;
      VueUtil.get("/knowledge/page", this.$route.query, (res) => {
        this.page = res.data;
        this.$nextTick(() => {
          this.currentPage = currentPage;
        });
      });
    },
    search() {
      this.page.startCursor = 0;
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.word)
      this.listProc();
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
    },
    addPage() {
      delete this.$route.query.knowledgeSeq;
      this.$router.push({ name: "knowledgeAdd", query: this.$route.query });
    },
    readPage(knowledgeSeq) {
      this.$route.query.knowledgeSeq = knowledgeSeq;
      this.$router.push({ name: "knowledgeRead", query: this.$route.query });
    },
    changePage(page) {
      let param = $.extend({}, this.$route.query);
      param["startCursor"] = this.page.returnCount * (page - 1);
      param["currentPage"] = page;
      this.$router.push({ name: "knowledgeList", query: param }).catch(err => {});
    },
  },
  mounted() {
    if (!this.$route.query.classifyC) {
      this.$route.query.classifyC = null;
    }
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
  .classify-col{
    width: 100px;
  }
  .content-col{
     max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .file-col{
    width: 60px;
  }
  .date-col{
    width: 140px;
  }
</style>

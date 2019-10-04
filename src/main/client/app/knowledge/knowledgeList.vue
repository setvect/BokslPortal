<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchParam.classifyC" size="sm">
          <option :value="null">--전체--</option>
          <option v-for="code in classifyList" :key="code.minorCode" :value="code.minorCode">{{code.codeValue}}</option>
        </b-form-select>
        <b-input @keypress.13="search()" v-model="searchWord" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list">
      <template slot="index" slot-scope="data">{{ data.index + 1 }}</template>
      <template slot="classifyC" slot-scope="data">{{data.item.classifyC}}</template>
      <template slot="problem" slot-scope="data">
        <b-link @click="readPage(data.item.knowledgeSeq)">{{clearHtml(data.item.problem)}}</b-link>
      </template>
      <template slot="solution" slot-scope="data">
        <b-link @click="readPage(data.item.knowledgeSeq)">{{clearHtml(data.item.solution)}}</b-link>
      </template>
      <template slot="regDate" slot-scope="data">{{data.item.regDate | dateFormat('YYYY-MM-DD')}}</template>
    </b-table>
    <b-pagination v-model="searchParam.currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
    <b-row>
      <b-col style="text-align:right">
        <b-button @click="addPage()" type="button" variant="info">만들기</b-button>
      </b-col>
    </b-row>
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
        { key: "regDate", label: "날짜", class: 'date-col' }
      ],
      searchParam: {
        classifyC: null,
        word: null,
        currentPage: 1
      },
      page: {
        total: 0,
        list: []
      },
      categoryList: [],
      searchWord: "",
    };
  },
  computed: {
    isSearch() {
      return this.searchParam.word;
    }
  },
  methods: {
    listProc() {
      VueUtil.get("/knowledge/page", this.searchParam, (res) => {
        this.page = res.data;
      });
    },
    search() {
      this.searchParam.word = this.searchWord;
      this.page.startCursor = 0;
      this.listProc();
    },
    searchCancel() {
      this.searchWord = "";
      this.searchParam.word = "";
      this.search();
    },
    addPage() {
      this.$router.push({ name: "knowledgeAdd" });
    },
    readPage() {
      this.$router.push({ name: "knowledgeRead" });
    },
    changePage(page) {
      console.log("page :", page);
    }
  },
  mounted() {
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
  .date-col{
    width: 140px;
  }
</style>

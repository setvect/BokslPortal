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
    <enumLayout :page="page" v-if="!isContentList" />
    <contentLayout :page="page" v-if="isContentList" />
    <b-pagination v-model="currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
  </div>
</template>

<script>
import boardCommon from "./mixin-boardArticle.js";
import enumLayout from "./boardArticleListEnum.vue";
import contentLayout from "./boardArticleListContent.vue";
// 내용 표시형
const CONTENT_VIEW_BOARD = ["BDAAAA02", "BDAAAA04", "BDAAAA05",];
export default {
  mixins: [boardCommon],
  data() {
    return {
      page: {
        totalCount: 0,
        list: []
      },
      currentPage: 1,
      isSearch: false
    };
  },
  components: {
    enumLayout, contentLayout
  },
  computed: {
    // true: 내용 표시형, false: 일반 목록형
    isContentList() {
      return CONTENT_VIEW_BOARD.includes(this.$route.query.boardCode);
    }
  },
  watch: {
    '$route.query.boardCode'() {
      this.$route.query.field = "title";
      this.search();
    }
  },
  methods: {
    listProc() {
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.word)
      VueUtil.get("/board-article/page", this.$route.query, (res) => {
        this.page = res.data;
        this.$nextTick(() => {
          this.currentPage = this.$route.query.currentPage;;
        });
      });
    },
    search() {
      let param = $.extend({}, this.$route.query);
      this.$route.query.startCursor = -1;
      param["startCursor"] = 0;
      this.$router
        .push({ name: "boardArticleList", query: param })
        .catch(err => { });
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
    },
    addPage() {
      delete this.$route.query.boardArticleSeq;
      this.$router.push({ name: "boardArticleAdd", query: this.$route.query });
    },
    changePage(page) {
      let param = $.extend({}, this.$route.query);
      param["startCursor"] = this.page.returnCount * (page - 1);
      param["currentPage"] = page;
      this.$router.push({ name: "boardArticleList", query: param }).catch(err => { });
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

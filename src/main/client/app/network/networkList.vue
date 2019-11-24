<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-input v-model="$route.query.word" @keypress.13.prevent="search()" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm" @click="search()">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info" style="margin-left:30px;">만들기</b-button>
      </b-form>
    </div>
    <b-row style="margin:0px;">
      <b-card
        v-for="item in page.list"
        :key="item.networkSeq"
        :title="item.title"
        tag="article"
        style="max-width: 20rem; margin-right:10px;cursor: pointer"
        class="mb-2"
        @click="readPage(item.networkSeq)"
      >
        <b-card-text>수정일: {{item.editDate | relativeDate }}</b-card-text>
      </b-card>
    </b-row>
    <div>
      <b-button @click="nextProc()" block variant="outline-secondary" size="sm">더보기({{page.list.length | numberFormat}}/{{page.totalCount | numberFormat}})</b-button>
    </div>
    <notifications group="message-noti" position="top center" />
  </div>
</template>

<script>
import '../../utils/vue-common.js';

export default {
  mixins: [comFunction],
  data() {
    return {
      page: {
        list: [],
        totalCount: -1,
      },
      isSearch: false,
    };
  },
  methods: {
    nextProc() {
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.word)
      this.$route.query.startCursor = this.page.list.length;
      if (this.$route.query.startCursor == this.page.totalCount) {
        this.$notify({
          group: 'message-noti',
          type: 'warn',
          text: '더 이상 없다.'
        });
      }
      VueUtil.get("/network/page", this.$route.query, (res) => {
        this.page.totalCount = res.data.totalCount;
        this.page.list = this.page.list.concat(res.data.list);
      });
    },
    search() {
      this.page.list = [];
      this.page.totalCount = -1;
      let param = $.extend({}, this.$route.query);
      this.$route.query.startCursor = -1;
      param["startCursor"] = 0;
      this.$router.push({ name: "networkList", query: param }).catch(err => { });
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
    },
    addPage() {
      this.$router.push({ name: "networkEdit" });
    },
    readPage(seq) {
      this.$route.query.networkSeq = seq;
      this.$router.push({ name: "networkEdit", query: this.$route.query });
    },
  },
  mounted() {
    this.nextProc();
  },
  beforeRouteUpdate(to, from, next) {
    next();
    this.nextProc();
  }
};
</script>

<style >
  .index-col{
    width: 50px;
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

<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.categorySeq" size="sm" style="width: 150px;" @change="loadListProc()">
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>
        <b-input @keypress.13.prevent="search()" v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info" style="margin-left:30px;">만들기</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="filterListData">
      <template slot="index" slot-scope="data">{{ listData.length - data.index }}</template>
      <template slot="content" slot-scope="data">
        <b-link @click="readPage(data.item.memoSeq)">{{ data.item.content }}</b-link>
      </template>
      <template slot="editDate" slot-scope="data">{{data.item.editDate | relativeDate}}</template>
      <template slot="delete" slot-scope="data">
        <b-link @click="deleteProc(data.item.memoSeq)">삭제</b-link>
      </template>
    </b-table>
  </div>
</template>

<script>
import '../../utils/vue-common.js';
import memoCommon from "./mixin-memo.js";
import store from "../../store/index.js";
import cookies from 'js-cookie';

export default {
  mixins: [memoCommon],
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "content", label: "내용", class: 'content-col' },
        { key: "editDate", label: "수정일", class: 'date-col' },
        { key: "delete", label: "삭제", class: 'delete-col' }
      ],
      listData: [
      ],
      filterWord: "",
      isSearch: false,
      page: {
        total: 300,
        perPage: 10
      },
      categoryList: [
      ]
    };
  },
  computed: {
    filterListData() {
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.filterWord);
      let filter = this.$route.query.filterWord || "";
      return this.listData.filter(item => item.content.includes(filter));
    }
  },
  methods: {
    addPage() {
      this.$route.query.categorySeq = this.searchData.categorySeq;
      this.$router.push({ name: "memoAdd", query: this.$route.query });
    },
    readPage(seq) {
      this.$route.query.memoSeq = seq;
      this.$route.query.categorySeq = this.searchData.categorySeq;
      this.$router.push({ name: "memoAdd", query: this.$route.query });
    },
    loadListProc() {
      cookies.set("BokslMemoCategory", this.searchData.categorySeq, {
        expires: 100
      });
      VueUtil.get(`/memo/list/${this.searchData.categorySeq}`, {}, (res) => {
        this.listData = res.data;
      });
    },
    loadCategoryProc() {
      this.categoryList = store.state.memo.categoryList;
      if (this.searchData.categorySeq == null) {
        this.searchData.categorySeq = this.categoryList[0].categorySeq;
      }
      this.searchData.categorySeq = this.$route.query.categorySeq || this.searchData.categorySeq;
      this.loadListProc();
    },
    search() {
      this.$router.push({ name: "memoList", query: { filterWord: this.searchData.word } }).catch(err => { });
    },
    searchCancel() {
      this.searchData.word = "";
      this.search();
    },
  },
  mounted() {
    this.searchData.categorySeq = cookies.get("BokslMemoCategory");
    if (!this.$route.query.filterWord) {
      this.$route.query.filterWord = "";
    }

    if (store.state.memo.categoryList) {
      this.loadCategoryProc();
    }
    else {
      store.dispatch('memo/loadCategory').then(() => {
        this.loadCategoryProc();
      });
    }
  },
  beforeRouteUpdate(to, from, next) {
    next();
    this.loadListProc();
  }

};
</script>

<style >
  .index-col{
    width: 30px;
  }
  .content-col{
     max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .date-col{
    width: 100px;
  }
  .delete-col{
    width: 60px;
  }
</style>

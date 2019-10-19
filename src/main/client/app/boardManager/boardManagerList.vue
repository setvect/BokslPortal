<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="$route.query.field" size="sm">
          <option value="name">이름</option>
          <option value="code">게시판코드</option>
        </b-form-select>
        <b-input @keypress.13.prevent="search()" v-model="$route.query.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm">검색</b-button>
        <b-button v-show="isSearch" @click="searchCancel()" variant="primary" size="sm">검색 취소</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info" style="margin-left:30px;">만들기</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list">
      <template slot="boardCode" slot-scope="data">
        <b-link @click="readPage(data.item.boardCode)">{{ data.item.boardCode }}</b-link>
      </template>
      <template slot="move" slot-scope="data">
        <router-link :to="{name: 'boardArticle', query: { boardCode: data.item.boardCode }}" target="_blank">바로가기</router-link>
      </template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.item.boardCode)">수정</b-link>
        <b-link @click="deleteProc(data.item.boardCode)">삭제</b-link>
      </template>
    </b-table>
    <b-pagination v-model="currentPage" :total-rows="page.totalCount" :per-page="10" @change="changePage" limit="10" align="center" />
  </div>
</template>

<script>
import boardCommon from './mixin-boardManager.js'

export default {
  mixins: [boardCommon],
  data() {
    return {
      fields: [
        { key: 'boardCode', label: "코드" },
        { key: 'move', label: "바로가기" },
        { key: 'name', label: "게시판이름" },
        { key: 'function', label: "기능" },
      ],
      page: {
        totalCount: 0,
        list: []
      },
      currentPage: 1,
      isSearch: false
    }
  },
  methods: {
    listProc() {
      let currentPage = this.$route.query.currentPage;
      VueUtil.get("/board-manager/page", this.$route.query, (res) => {
        this.page = res.data;
        this.$nextTick(() => {
          this.currentPage = currentPage;
        });
      });
    },
    search() {
      this.$route.query.startCursor = 0;
      console.log('this.$route.query.word :', this.$route.query.word);
      this.isSearch = !CommonUtil.isEmpty(this.$route.query.word)
      this.listProc();
    },
    searchCancel() {
      this.$route.query.word = "";
      this.search();
    },
    addPage() {
      delete this.$route.query.boardCode;
      this.$router.push({ name: 'boardManagerAdd', query: this.$route.query })
    },
    readPage(boardCode) {
      this.$route.query.boardCode = boardCode;
      this.$router.push({ name: "boardManagerRead", query: this.$route.query });
    },
    changePage(page) {
      this.$route.query.startCursor = this.page.returnCount * (page - 1)
      this.$route.query.currentPage = page;
      this.listProc();
    }
  },
  mounted() {
    if (!this.$route.query.field) {
      this.$route.query.field = "name";
    }
    this.listProc();
  }
}
</script>
<template>
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
</template>

<script>
import boardCommon from "./mixin-boardArticle.js";

export default {
  mixins: [boardCommon],
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "title", label: "제목" },
        { key: "regDate", label: "날짜", class: 'date-col' },
        { key: "function", label: "기능", class: 'function-col' }
      ],
    };
  },
  props: {
    page: {
      type: Object
    }
  },
  methods: {
    readPage(boardArticleSeq) {
      this.$route.query.boardArticleSeq = boardArticleSeq;
      this.$router.push({ name: "boardArticleRead", query: this.$route.query });
    },
  },
  mounted() {
  },
  beforeRouteUpdate(to, from, next) {
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

<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.category" size="sm">
          <option :value="null">--전체--</option>
          <option v-for="category in categoryList" :key="category.categorySeq">{{category.name}}</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="listData">
      <template slot="index" slot-scope="data">{{ data.index + 1 }}</template>
      <template slot="question" slot-scope="data"><b-link @click="readPage(data.item.knowledgeSeq)">{{ data.item.question }}</b-link></template>
      <template slot="answer" slot-scope="data"><b-link @click="readPage(data.item.knowledgeSeq)">{{ data.item.answer }}</b-link></template>
    </b-table>
    <b-pagination v-model="searchData.currentPage" :total-rows="page.total" :per-page="page.perPage" @change="changePage" limit="10" align="center"/>
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
  mixins: [knowledgeCommon],
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "question", label: "질문", class: 'content-col' },
        { key: "answer", label: "답변", class: 'content-col' },
        { key: "regDate", label: "날짜", class: 'date-col' }
      ],
      listData: [
        {
          knowledgeSeq: 1,
          question: "질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. ",
          answer: "답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. ",
          regDate: 1561071320000
        }, {
          knowledgeSeq: 2,
          question: "질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. ",
          answer: "답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. ",
          regDate: 1561071320000
        },
      ],
      searchData: {
        category: null,
        word: null,
        currentPage: 1
      },
      page: {
        total: 300,
        perPage: 10
      },
      categoryList: [
        {
          categorySeq: 1,
          name: "언어"
        }, {
          categorySeq: 2,
          name: "DBMS"
        }, {
          categorySeq: 3,
          name: "OS셋팅"
        },
      ]
    };
  },
  methods: {
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

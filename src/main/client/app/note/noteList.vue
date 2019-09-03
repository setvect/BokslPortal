<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.field" size="sm">
          <option value="name">이름</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="listData">
      <template slot="index" slot-scope="data" style>{{ data.index + 1 }}</template>
      <template slot="title" slot-scope="data">
        <b-link @click="readPage(data.item.noteSeq)">{{ data.item.title }}</b-link>
      </template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.noteSeq)">수정</b-link>
        <b-link @click="deleteProc(data.noteSeq)">삭제</b-link>
      </template>
    </b-table>
    <b-pagination v-model="searchData.currentPage" :total-rows="page.total" :per-page="page.perPage" @change="changePage" limit="10" align="center" />
    <b-row>
      <b-col style="text-align:right">
        <b-button @click="addPage()" type="button" variant="info">만들기</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import store from "../../store/index.js";

export default {
  mixins: [noteCommon],
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "title", label: "제목" },
        { key: "function", label: "기능", class: 'function-col' }
      ],
      listData: [
        {
          noteSeq: 1,
          title: "제목입니다1"
        },
        {
          noteSeq: 2,
          title: "제목입니다2"
        },
        {
          noteSeq: 3,
          title: "제목입니다31"
        }
      ],
      searchData: {
        field: "name",
        word: null,
        currentPage: 1
      },
      page: {
        total: 300,
        perPage: 10
      }
    };
  },
  methods: {
    addPage() {
      this.$router.push({ name: "noteAdd" });
    },
    readPage() {
      this.$router.push({ name: "noteRead" });
    },
    changePage(page) {
      console.log("page :", page);
    }
  },
  mounted() {
    store.dispatch('note/loadTree');
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
</style>

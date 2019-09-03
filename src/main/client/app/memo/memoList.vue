<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.categorySeq" size="sm" style="width: 150px;" @change="loadListProc(searchData.categorySeq)">
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>
        <b-input @keypress.13="search()" v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info">만들기</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="filterListData">
      <template slot="index" slot-scope="data">{{ data.index + 1 }}</template>
      <template slot="content" slot-scope="data">
        <b-link @click="readPage(data.item.memoSeq)">{{ data.item.content }}</b-link>
      </template>
      <template slot="editDate" slot-scope="data">{{data.item.editDate | dateFormat('YYYY-MM-DD')}}</template>
      <template slot="delete" slot-scope="data">
        <b-link @click="deleteProc(data.item.memoSeq)">삭제</b-link>
      </template>
    </b-table>
  </div>
</template>

<script>
import '../../utils/vue-common.js'
import store from "../../store/index.js";

export default {
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
      searchData: {
        categorySeq: 2,
        word: null,
      },
      filterWord: "",
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
      return this.listData.filter(item => item.content.includes(this.filterWord));
    }
  },
  methods: {
    addPage() {
      this.$router.push({ name: "memoAdd", query: { categorySeq: this.searchData.categorySeq } });
    },
    readPage(seq) {
      this.$router.push({ name: "memoAdd", query: { categorySeq: this.searchData.categorySeq, memoSeq: seq } });
    },
    loadCategoryProc() {
      this.categoryList = store.state.memo.categoryList;
      this.searchData.categorySeq = this.$route.query.categorySeq || this.searchData.categorySeq;
      this.loadListProc();
    },
    loadListProc() {
      VueUtil.get(`/memo/list/${this.searchData.categorySeq}`, {}, (res) => {
        this.listData = res.data;
      });
    },
    search() {
      this.filterWord = this.searchData.word;
    },
    deleteProc(memoSeq) {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/memo/item/${memoSeq}`, {}, (res) => {
          this.loadListProc(this.searchData.categorySeq);
        });
      });
    },
  },
  mounted() {
    if (store.state.memo.categoryList) {
      this.loadCategoryProc();
    }
    else {
      store.dispatch('memo/loadCategory').then((r) => {
        this.loadCategoryProc();
      });
    }
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
  .delete-col{
    width: 100px;
  }
</style>

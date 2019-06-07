<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.field" size="sm">
          <option value="name">이름</option>
          <option value="code">게시판코드</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="listData">
      <template slot="index" slot-scope="data">{{ data.index + 1 }}</template>
      <template slot="boardCode" slot-scope="data">
        <b-link @click="readPage(data.item.boardManagerSeq)">{{ data.item.boardCode }}</b-link>
      </template>
      <template slot="function" slot-scope="data">
        <b-link @click="editPage(data.boardManagerSeq)">수정</b-link>
        <b-link @click="removeProc(data.boardManagerSeq)">삭제</b-link>
      </template>
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
export default {
  data() {
    return {
      fields: [
        { key: 'index', label: "#" },
        { key: 'boardCode', label: "코드" },
        { key: 'name', label: "게시판이름" },
        { key: 'function', label: "기능" },
      ],
      listData: [{
        boardManagerSeq: 1,
        boardCode: 'BDAAAA01',
        name: '메인화면',
      }, {
        boardManagerSeq: 2,
        boardCode: 'BDAAAA02',
        name: '글',
      }, {
        boardManagerSeq: 3,
        boardCode: 'BDAAAA03',
        name: '꿈',
      }],
      searchData: {
        field: "name",
        word: null,
        currentPage: 1
      },
      page: {
        total: 300,
        perPage: 10
      }
    }
  },
  methods: {
    editPage() {
      console.log("수정")
    },
    removeProc() {
      console.log("삭제")
    },
    changePage(page) {
      console.log("page", page);
    },
    addPage() {
      this.$router.push({ name: 'board-manager-add' })
    },
    readPage() {
      this.$router.push({ name: 'boardManagerRead' })
    },
    changePage(page) {
      console.log('page :', page);
    }
  }
}
</script>
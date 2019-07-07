<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.categorySeq" size="sm" style="width: 150px;">
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-row style="margin:0px;">
      <b-card v-for="item in listData" :key="item.networkSeq" :title="item.title" tag="article" style="max-width: 20rem; margin-right:10px;" class="mb-2" @click="readPage(item.networkSeq)">
        <b-card-text>수정일: {{item.editDate | dateFormat('YYYY-MM-DD') }}</b-card-text>
      </b-card>
    </b-row>
    <b-row>
      <b-col style="text-align:right">
        <b-button @click="addPage()" type="button" variant="info">만들기</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import '../../utils/vue-common.js'

export default {
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "content", label: "내용", class: 'content-col' },
        { key: "editDate", label: "수정일", class: 'date-col' }
      ],
      listData: [
        {
          networkSeq: 1,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
        {
          networkSeq: 2,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
        {
          networkSeq: 3,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
        {
          networkSeq: 4,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
        {
          networkSeq: 5,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
        {
          networkSeq: 6,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
        {
          networkSeq: 7,
          title: "Lorem ipsum dolor sit amet consectetur adipisicing elit.",
          editDate: 1561071320000
        },
      ],
      searchData: {
        categorySeq: 2,
        word: null,
      },
      page: {
        total: 300,
        perPage: 10
      },
      categoryList: [
        {
          categorySeq: 1,
          name: "생활"
        }, {
          categorySeq: 2,
          name: "생각"
        }, {
          categorySeq: 3,
          name: "일"
        },
      ]
    };
  },
  methods: {
    addPage() {
      this.$router.push({ name: "networkRead" });
    },
    readPage(seq){
      this.$router.push({ name: "networkEdit" });
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

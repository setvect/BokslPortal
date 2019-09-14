<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.field" size="sm">
          <option value="name">이름</option>
          <option value="content">내용</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info">만들기</b-button>
        <b-button @click="categoryForm()" size="sm" type="button" variant="outline-primary" style="float:right">카테고리</b-button>
      </b-form>
    </div>
    <div>
      <ol class="breadcrumb">
        <li v-for="category in categoryPath" :key="category.categorySeq" class="breadcrumb-item" :class="{'active': category.categorySeq == currentCategorySeq}">{{category.name}}</li>
      </ol>
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
    <note-category ref="categoryCmp" />
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import noteCategoryComponent from "./noteCategory.vue";
import store from "../../store/index.js";
import { DraggableTree } from 'vue-draggable-nested-tree';

export default {
  mixins: [noteCommon],
  components: {
    noteCategory: noteCategoryComponent
  },
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
      },
      currentCategorySeq: 0,
      categoryPath: []
    };
  },
  watch: {
    '$route.query.categorySeq'() {
      this.currentCategorySeq = this.$route.query.categorySeq;
      this.getCategoryPath();
    }
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
    },
    categoryForm() {
      this.$refs['categoryCmp'].open();
    },
    getCategoryPath() {
      if (!this.currentCategorySeq) {
        this.categoryPath = [{ name: "홈" }];
        return;
      }
      VueUtil.get(`/note/category-path/${this.currentCategorySeq}`, {}, (res) => {
        this.categoryPath = res.data.slice(1);
      });
    }
  },
  mounted() {
    this.currentCategorySeq = parseInt(this.$route.query.categorySeq);
    this.getCategoryPath();
  }
};
</script>

<style lang="scss">
.index-col {
  width: 50px;
}
.function-col {
  width: 140px;
}
</style>

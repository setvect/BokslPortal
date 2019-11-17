<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group label="카테고리">
        <b-form-select v-model="item.categorySeq" size="sm">
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>
      </b-form-group>

      <b-form-group label="내용">
        <span class="reg-edit-date" style="margin-top:-26px;">등록일: {{ item.regDate | relativeDate }}, 수정일: {{ item.editDate | relativeDate }}</span>
        <b-form-textarea v-model="item.content" v-validate="{ required: true}" :state="validateState('content')" name="content" data-vv-as="내용" placeholder="내용 넣어라" style="height:300px;"></b-form-textarea>
        <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button @click="listPage($route.query.categorySeq)" type="button" variant="info">취소</b-button>
        </b-col>
        <b-col cols="auto">
          <b-button @click="submitProc()" type="button" variant="info">확인</b-button>
        </b-col>
      </b-row>
    </form>
  </div>
</template>
<script>
import store from "../../store/index.js";

export default {
  mixins: [comFunction],
  data() {
    return {
      item: {},
      categoryList: [
      ]
    };
  },
  methods: {
    init() {
      this.categoryList = store.state.memo.categoryList;

      if (this.$route.query.memoSeq) {
        VueUtil.get(`/memo/item/${this.$route.query.memoSeq}`, {}, (res) => {
          this.item = res.data;
          this.item.categorySeq = this.$route.query.categorySeq;
        });
      } else {
        this.item = {};
        this.item.categorySeq = this.$route.query.categorySeq;
      }
    },
    listPage(categorySeq) {
      this.$router.push({ name: "memoList", query: { categorySeq: categorySeq } });
    },

    submitProc() {
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }

        if (this.item.memoSeq) {
          this.editProc();
        } else {
          this.addProc();
        }
      });
    },
    addProc() {
      VueUtil.post("/memo/item", this.item, (res) => {
        this.listPage(this.$route.query.categorySeq);
      });
    },
    editProc() {
      this.item.category = null;
      VueUtil.put("/memo/item", this.item, (res) => {
        this.listPage(this.$route.query.categorySeq);
      });
    },
  },
  mounted() {
    if (store.state.memo.categoryList) {
      this.init();
    }
    else {
      store.dispatch('memo/loadCategory').then((r) => {
        this.init();
      });
    }
  }
};
</script>
<style scoped>
</style>
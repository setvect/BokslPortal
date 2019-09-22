<template>
  <div>
    <h5>{{item.title}}</h5>
    <b-row>
      <b-col sm="12" class="content" v-html="item.content" />
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>첨부파일</label>
      </b-col>
      <b-col sm="10">
        <ul>
          <li v-for="attach in item.attachList" :key="attach.attachSeq">{{attach.name}} (size: {{attach.size | numberFormat}} byte )</li>
        </ul>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-button @click="listPage()" type="button" variant="info">목록</b-button>
      </b-col>
      <b-col cols="auto">
        <b-button @click="editPage(item.noteSeq)" type="button" variant="info">수정</b-button>
        <b-button @click="deleteProc(item.noteSeq)" type="button" variant="danger">삭제</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import "../../utils/vue-common.js";

export default {
  mixins: [noteCommon],
  data() {
    return {
      item: {}
    };
  },
  methods: {
    init() {
      VueUtil.get(`/note/item/${this.$route.query.noteSeq}`, {}, (res) => {
        this.item = res.data;
      });
    }
  },
  mounted() {
    this.init();
  }
};
</script>
<style scoped>
.head {
  font-weight: bold;
}
.content{
  border:1px solid #ccc!important
}
</style>

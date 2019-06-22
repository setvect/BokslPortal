<template>
  <div>
    <h5>질문</h5>
    <b-row>
      <b-col sm="12" v-html="item.question"/>
    </b-row>
    <h5 style="padding-top:30px;">답변</h5>
    <b-row>
      <b-col sm="12" v-html="item.answer"/>
    </b-row>
    <b-row style="padding-top:30px;">
      <b-col sm="12">
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
        <b-button @click="editPage()" type="button" variant="info">수정</b-button>
        <b-button @click="deleteProc()" type="button" variant="danger">삭제</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import knowledgeCommon from "./mixin-knowledge.js";
import "../../utils/vue-common.js";

export default {
  mixins: [knowledgeCommon],
  data() {
    return {
      item: {
        knowledgeSeq: 1,
        question: "질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. 질문입니다. ",
        answer: "답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. 답변입니다. ",
        regDate: 1561071320000,
        attachList: [
          {
            attachSeq: 1,
            name: "abc.ppt",
            size: 500000
          },
          {
            attachSeq: 2,
            name: "bee.ppt",
            size: 1000
          }
        ]
      }
    };
  },
  methods: {
    listPage() {
      this.$router.push({ name: "knowledgeList" });
    }
  }
};
</script>
<style scoped>
.head {
  font-weight: bold;
}
</style>

<template>
  <div>
    <h5>{{item.title}}</h5>
    <b-row>
      <b-col sm="2" class="head">
        <label>내용</label>
      </b-col>
      <b-col sm="10" v-html="item.content"/>
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>첨부파일</label>
      </b-col>
      <b-col sm="10">
        <ul>
          <li
            v-for="attach in item.attachList"
            :key="attach.attachSeq"
          >{{attach.name}} (size: {{attach.size | numberFormat}} byte )</li>
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
        title: "노트..",
        content: "공지사항<br><strong>안녕</strong>",
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

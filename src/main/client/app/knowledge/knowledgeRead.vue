<template>
  <div>
    <b-row>
      <b-col></b-col>
      <b-col cols="auto">
        <b-button @click="listPage()" size="sm" type="button" variant="info">목록</b-button>
        <b-button @click="editPage(item.noteSeq)" size="sm" type="button" variant="info">수정</b-button>
        <b-button @click="deleteProc(item.noteSeq)" size="sm" type="button" variant="danger">삭제</b-button>
      </b-col>
    </b-row>

    <h5>
      분류: {{ item.classifyC }} <span class="reg-edit-date">등록일: {{ item.regDate | relativeDate }}</span>
    </h5>
    <h5 style="padding-top:10px;">질문</h5>
    <b-row>
      <b-col sm="12" v-html="item.problem" class="_content" />
    </b-row>
    <h5 style="padding-top:30px;">답변</h5>
    <b-row>
      <b-col sm="12" v-html="item.solution" class="_content" />
    </b-row>
    <b-row>
      <b-col>
        <ul>
          <li v-for="attach in item.attach" :key="attach.attachSeq">
            <b-button @click="downloadFile(attach.attachFileSeq, attach.originalName)" type="button" variant="outline-secondary" size="sm">{{ attach.originalName }} (size: {{ attach.size | numberFormat }} byte )</b-button>
          </li>
        </ul>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-button @click="listPage()" type="button" variant="info">목록</b-button>
      </b-col>
      <b-col cols="auto">
        <b-button @click="editPage(item.knowledgeSeq)" type="button" variant="info">수정</b-button>
        <b-button @click="deleteProc(item.knowledgeSeq)" type="button" variant="danger">삭제</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import knowledgeCommon from "./mixin-knowledge.js";
import "../../utils/vue-common.js";

export default {
  mixins: [comFunction, knowledgeCommon],
  data() {
    return {
      item: {}
    };
  },
  methods: {
    init() {
      VueUtil.get(`/knowledge/item/${this.$route.query.knowledgeSeq}`, {}, res => {
        this.item = res.data;
        this.fitImage("._content img");
      });
    },
    editPage() {
      this.$router.push({
        name: "knowledgeAdd",
        query: this.$route.query
      });
    },
    deleteProc(knowledgeSeq) {
      Swal.fire({
        title: "삭제할거야?",
        type: "info",
        showCloseButton: true,
        showCancelButton: true
      }).then(result => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/knowledge/item/${knowledgeSeq}`, {}, res => {
          this.listPage();
        });
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
</style>

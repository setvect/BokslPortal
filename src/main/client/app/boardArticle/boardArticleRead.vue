<template>
  <div>
    <h5>{{item.title}}</h5>
    <b-row>
      <b-col sm="2" class="head">
        <label>내용</label>
      </b-col>
      <b-col sm="10" v-html="item.content" />
    </b-row>
    <b-row>
      <b-col sm="2" class="head">
        <label>첨부파일</label>
      </b-col>
      <b-col sm="10">
        <ul>
          <li v-for="attach in item.attach" :key="attach.attachSeq">
            <b-button
              @click="downloadFile(attach.attachFileSeq, attach.originalName)"
              type="button"
              variant="outline-secondary"
              size="sm"
            >{{attach.originalName}} (size: {{attach.size | numberFormat}} byte )</b-button>
          </li>
        </ul>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-button @click="listPage()" type="button" variant="info">목록</b-button>
      </b-col>
      <b-col cols="auto">
        <b-button @click="editPage(item.boardArticleSeq)" type="button" variant="info">수정</b-button>
        <b-button @click="deleteProc(item.boardArticleSeq)" type="button" variant="danger">삭제</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import boardCommon from "./mixin-boardArticle.js";
import "../../utils/vue-common.js";

export default {
  mixins: [comFunction, boardCommon],
  data() {
    return {
      item: {}
    };
  },
  methods: {
    init() {
      VueUtil.get(`/board-article/item/${this.$route.query.boardArticleSeq}`, {}, (res) => {
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
</style>

<template>
  <div>
    <h5>
      {{ item.title }}
      <span class="reg-edit-date">등록일: {{item.regDate | relativeDate}}</span>
    </h5>
    <b-row v-if="!isEncryptInput">
      <b-col v-html="item.content" class="_content" />
    </b-row>
    <b-row v-if="isEncryptInput">
      <b-col sm="2" class="head">
        <label>암호문자</label>
      </b-col>
      <b-col>
        <b-input-group class="mt-3">
          <b-form-input @keypress.13.prevent="encryptProc()" v-model="encrypt" placeholder="암호문자를 입력해라."></b-form-input>
          <b-input-group-append>
            <b-button @click="encryptProc()" variant="outline-success">확인</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <ul>
          <li v-for="attach in filterImageFiles(item.attach)" :key="attach.attachSeq">
            <b-img thumbnail fluid :src="getThumUrl(attach)" :title="attach.originalName" @click="openImage(attach)"></b-img>
          </li>
        </ul>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <ul>
          <li v-for="attach in item.attach" :key="attach.attachSeq">
            <b-button
              @click="downloadFile(attach.attachFileSeq, attach.originalName)"
              type="button"
              variant="outline-secondary"
              size="sm"
            >{{ attach.originalName }} (size: {{ attach.size | numberFormat }} byte )</b-button>
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
      item: {
        attach: []
      }
    };
  },
  methods: {
    init() {
      VueUtil.get(`/board-article/item/${this.$route.query.boardArticleSeq}`, {}, res => {
        this.item = res.data;
        this.fitImage("._content img");
      });
    },
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

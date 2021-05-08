<template>
  <div>
    <b-row>
      <b-col></b-col>
      <b-col cols="auto">
        <b-button @click="listPage()" size="sm" type="button" variant="info"
          >목록</b-button
        >
        <b-button
          @click="editPage(item.noteSeq)"
          size="sm"
          type="button"
          variant="info"
          >수정</b-button
        >
        <b-button
          @click="deleteProc(item.noteSeq)"
          size="sm"
          type="button"
          variant="danger"
          >삭제</b-button
        >
      </b-col>
    </b-row>

    <h5>
      {{ item.title }}
      <span class="reg-edit-date"
        >등록일: {{ item.regDate | relativeDate }}, 수정일:
        {{ item.editDate | relativeDate }}</span
      >
    </h5>
    <b-row>
      <b-col class="_content" v-if="!item.markdownF" v-html="item.content" />
      <b-col class="_content" v-if="item.markdownF" >
        <MarkdownItVue :content="item.content"/>
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
            >
              {{ attach.originalName }} (size:
              {{ attach.size | numberFormat }} byte )
            </b-button>
          </li>
        </ul>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-button @click="listPage()" type="button" variant="info"
          >목록</b-button
        >
      </b-col>
      <b-col cols="auto">
        <b-button @click="editPage(item.noteSeq)" type="button" variant="info"
          >수정</b-button
        >
        <b-button
          @click="deleteProc(item.noteSeq)"
          type="button"
          variant="danger"
          >삭제</b-button
        >
      </b-col>
    </b-row>
  </div>
</template>

<script>
import noteCommon from "./mixin-note.js";
import "../../utils/vue-common.js";
import MarkdownItVue from 'markdown-it-vue'

import 'markdown-it-vue/dist/markdown-it-vue.css'

export default {
  mixins: [comFunction, noteCommon],
  data() {
    return {
      item: {}
    };
  },
  computed: {
    htmlContent() {
    }
  },
  components: {
    MarkdownItVue
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      VueUtil.get(`/note/item/${this.$route.query.noteSeq}`, {}, res => {
        this.item = res.data;
        this.fitImage("._content img");
      });
    },
  },
};
</script>
<style scoped>
.head {
  font-weight: bold;
}
.content {
  border: 1px solid #ccc !important;
}
</style>

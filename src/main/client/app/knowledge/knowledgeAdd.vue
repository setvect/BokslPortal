<template>
  <div>
    <h5>등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-select v-model="item.classifyC" size="sm">
          <option :value="null">== 선택 ==</option>
          <option v-for="code in classifyList" :key="code.minorCode" :value="code.minorCode">{{code.codeValue}}</option>
        </b-form-select>
      </b-form-group>

      <b-form-group>
        <textarea v-model="item.question" id="question" rows="10" cols="100" style="width: 100%; height: 250px; display:none"></textarea>
      </b-form-group>
      <b-form-group>
        <textarea v-model="item.answer" id="answer" rows="10" cols="100" style="width: 100%; height: 250px; display:none"></textarea>
      </b-form-group>
      <b-form-group>
        <b-form-file v-model="item.attach" :multiple="true" placeholder="첨부파일" />
      </b-form-group>
      <b-row>
        <b-col>
          <b-button @click="listPage()" type="button" variant="info">취소</b-button>
        </b-col>
        <b-col cols="auto">
          <b-button @click="submitProc()" type="button" variant="info">확인</b-button>
        </b-col>
      </b-row>
    </form>
    <impageUploadComponent @pasted="pasteImage" ref="imageUpload" />
  </div>
</template>
<script>
import knowledgeCommon from "./mixin-knowledge.js";
import impageUploadComponent from "../common/imageUpload/imageUpload.vue";
import store from "../../store/index.js";
import "../../utils/vue-common.js";
import '../../asserts/lib/editor/js/HuskyEZCreator.js';

export default {
  mixins: [comFunction, knowledgeCommon],
  components: {
    impageUploadComponent
  },
  data() {
    return {
      item: {
        knowledgeSeq: 1,
        question: "",
        answer: "",
        attachList: [],
        classifyC: null,
      },
      classifyList: [],
      questionEdit: [],
      answerEdit: [],
      editor: null,
    };
  },
  methods: {
    initEditor() {
      // 질문
      nhn.husky.EZCreator.createInIFrame({
        oAppRef: this.questionEdit,
        elPlaceHolder: "question",
        sSkinURI: "/asserts/editor/SmartEditor2Skin.html",
        fCreator: "createSEditorInIFrame",
        fOnAppLoad: () => {
          // 본문 내용 수정
          $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(e => {
            this.item.content = this.questionEdit.getById["question"].getIR();
          });
          this.questionEdit.getById["question"].setDefaultFont("나눔고딕", 10);
        },
      });

      // 답변
      nhn.husky.EZCreator.createInIFrame({
        oAppRef: this.answerEdit,
        elPlaceHolder: "answer",
        sSkinURI: "/asserts/editor/SmartEditor2Skin.html",
        fCreator: "createSEditorInIFrame",
        fOnAppLoad: () => {
          // 본문 내용 수정
          $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(e => {
            this.item.content = this.answerEdit.getById["answer"].getIR();
          });
          this.answerEdit.getById["answer"].setDefaultFont("나눔고딕", 10);
        },
      });
    },
    // 이미지 붙이기
    pasteImage(html) {
      this.editor.exec("PASTE_HTML", [html]);
    },
    submitProc() {
      console.log("submit");
    },
    loadCodeList() {
      VueUtil.get('/code/list/KNOW_TYPE', {}, (res) => {
        this.classifyList = res.data;
      });
    }
  },
  mounted() {
    this.initEditor();
    this.loadCodeList();
    window.openImageForm = (editor) => {
      this.editor = editor;
      this.$refs['imageUpload'].open();
    }
  }
};
</script>
<style >
  .ck-editor__editable {
    min-height: 300px;
  }
</style>
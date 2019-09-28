<template>
  <div>
    <h5>등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <textarea v-model="item.question" id="question" rows="10" cols="100" style="width: 100%; height: 350px; display:none"></textarea>
      </b-form-group>
      <b-form-group>
        <textarea v-model="item.answer" id="answer" rows="10" cols="100" style="width: 100%; height: 350px; display:none"></textarea>
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
      },
      editorOption: {
        placeholder: '내용을 입력하세요.',
      },
      questionEdit: [],
      answerEdit: [],
    };
  },
  methods: {
    initEditor() {

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
    submitProc() {
      console.log("submit");
    },
  },
  mounted() {
    this.initEditor();

    window.openImageForm = (a) => {
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
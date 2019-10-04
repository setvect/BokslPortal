<template>
  <div>
    <h5>등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-select v-model="item.classifyC" :state="validateState('classifyC')" v-validate="{ required: true }" name="classifyC" data-vv-as="분류" placeholder="분류 선택해라" size="sm">
          <option :value="null">== 선택 ==</option>
          <option v-for="code in classifyList" :key="code.minorCode" :value="code.minorCode">{{code.codeValue}}</option>
        </b-form-select>
        <span v-show="!validateState('classifyC')" class="invalid-feedback">{{ veeErrors.first('classifyC') }}</span>
      </b-form-group>
      <b-form-group>
        <textarea v-model="item.problem" id="problem" rows="10" cols="100" style="width: 100%; height: 250px; display:none"></textarea>
      </b-form-group>
      <b-form-group>
        <textarea v-model="item.solution" id="solution" rows="10" cols="100" style="width: 100%; height: 250px; display:none"></textarea>
      </b-form-group>
      <b-form-group>
        <b-form-file @change="attachFile($event)" :multiple="true" placeholder="첨부파일" />
      </b-form-group>
      <b-form-group v-show="item.attach.length !== 0">
        <ul>
          <li v-for="attach in item.attach" :key="attach.attachSeq">
            <b-check v-model="deleteAttachFileSeq" :value="attach.attachFileSeq">{{attach.originalName}} (size: {{attach.size | numberFormat}} byte )</b-check>
          </li>
        </ul>
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
        problem: "",
        solution: "",
        attachList: [],
        classifyC: null,
        attach: [],
      },
      deleteAttachFileSeq: [],
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
        elPlaceHolder: "problem",
        sSkinURI: "/asserts/editor/SmartEditor2Skin.html",
        fCreator: "createSEditorInIFrame",
        fOnAppLoad: () => {
          // 본문 내용 수정
          $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(e => {
            this.item.problem = this.questionEdit.getById["problem"].getIR();
          });
          this.questionEdit.getById["problem"].setDefaultFont("나눔고딕", 10);
        },
      });

      // 답변
      nhn.husky.EZCreator.createInIFrame({
        oAppRef: this.answerEdit,
        elPlaceHolder: "solution",
        sSkinURI: "/asserts/editor/SmartEditor2Skin.html",
        fCreator: "createSEditorInIFrame",
        fOnAppLoad: () => {
          // 본문 내용 수정
          $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(e => {
            this.item.solution = this.answerEdit.getById["solution"].getIR();
          });
          this.answerEdit.getById["solution"].setDefaultFont("나눔고딕", 10);
        },
      });
    },
    // 이미지 붙이기
    pasteImage(html) {
      this.editor.exec("PASTE_HTML", [html]);
    },
    submitProc() {
      let problem = CommonUtil.clearHtml(this.item.problem);
      if (CommonUtil.isEmpty(problem)) {
        Swal.fire(
          '안내',
          '질문을 입력해',
          'error'
        )
        return;
      }

      this.$validator.validateAll().then((result) => {
        if (!result) {
          return;
        }

        let url;
        // 수정
        if (this.$route.query.knowledgeSeq) {
          url = "/knowledge/item-edit";
          this.item.deleteAttachFileSeq = this.deleteAttachFileSeq;
        }
        // 등록
        else {
          url = "/knowledge/item";
        }
        delete this.item.attach;
        delete this.item.classifyCode;
        VueUtil.post(url, this.item, (res) => {
          this.$router.push({ name: "knowledgeList", query: this.$route.query });
        }, { "call-type": "multipart" });
      });
    },
    attachFile(event) {
      this.item.attachList = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.item.attachList.push(event.target.files[i]);
      }
    },
  },
  mounted() {
    // 수정
    if (this.$route.query.knowledgeSeq) {
      VueUtil.get(`/knowledge/item/${this.$route.query.knowledgeSeq}`, {}, (res) => {
        this.item = res.data;
        this.initEditor();
      });
    }
    // 등록
    else {
      this.initEditor();
    }
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
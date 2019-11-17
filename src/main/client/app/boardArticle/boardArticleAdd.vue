<template>
  <div>
    <h5>글쓰기</h5>
    <form autocomplete="off">
      <b-form-group abel-cols="2" label-cols-lg="2" label="제목">
        <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('title')" name="title" data-vv-as="제목"></b-form-input>
        <span v-show="!validateState('title')" class="invalid-feedback">{{ veeErrors.first("title") }}</span>
      </b-form-group>
      <b-form-group v-if="!isEncryptInput" label-cols="2" label-cols-lg="2" label="내용">
        <textarea v-model="item.content" id="content" rows="10" cols="100" style="width: 100%; height: 350px; display:none"></textarea>
      </b-form-group>
      <b-form-group v-if="isEncryptInput" label-cols="2" label-cols-lg="2" label="암호문자">
        <b-input-group class="mt-3">
          <b-form-input @keypress.13.prevent="encryptRun()" v-model="encrypt" placeholder="암호문자를 입력해라."></b-form-input>
          <b-input-group-append>
            <b-button @click="encryptRun()" variant="outline-success">확인</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-form-group>

      <b-form-group v-if="isEncrypt" abel-cols="2" label-cols-lg="2" label="암호 문자열">
        <b-form-input v-model="item.encrypt"></b-form-input>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="첨부파일">
        <b-form-file @change="attachFile($event)" :multiple="true" placeholder="첨부파일" />
      </b-form-group>
      <b-form-group>
        <ul>
          <li v-for="attach in item.attach" :key="attach.attachSeq">
            <b-check v-model="deleteAttachFileSeq" :value="attach.attachFileSeq">{{ attach.originalName }} (size: {{ attach.size | numberFormat }} byte )</b-check>
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
import boardCommon from "./mixin-boardArticle.js";
import impageUploadComponent from "../common/imageUpload/imageUpload.vue";
import "../../utils/vue-common.js";
import "../../asserts/lib/editor/js/HuskyEZCreator.js";

export default {
  mixins: [comFunction, boardCommon],
  components: {
    impageUploadComponent
  },
  data() {
    return {
      item: {
        title: "",
        content: "",
        boardCode: this.$route.query.boardCode,
        attachList: [],
        attach: []
      },
      deleteAttachFileSeq: [],
      oEditors: []
    };
  },
  methods: {
    initEditor() {
      nhn.husky.EZCreator.createInIFrame({
        oAppRef: this.oEditors,
        elPlaceHolder: "content",
        sSkinURI: "/asserts/editor/SmartEditor2Skin.html",
        fCreator: "createSEditorInIFrame",
        fOnAppLoad: () => {
          // 본문 내용 수정
          $("iframe")
            .contents()
            .find("#se2_iframe")
            .contents()
            .find("body");
          this.oEditors.getById["content"].setDefaultFont("나눔고딕", 10);
        }
      });
    },
    // 이미지 붙이기
    pasteImage(html) {
      this.oEditors.getById["content"].exec("PASTE_HTML", [html]);
    },
    submitProc() {
      this.item.content = this.oEditors.getById["content"].getIR();
      let html = CommonUtil.clearHtml(this.item.content);
      if (CommonUtil.isEmpty(html)) {
        Swal.fire("안내", "내용을 입력해", "error");
        return;
      }
      this.$validator.validateAll().then(result => {
        if (!result) {
          return;
        }
        let url;
        // 수정
        if (this.$route.query.boardArticleSeq) {
          url = "/board-article/item-edit";
          this.item.deleteAttachFileSeq = this.deleteAttachFileSeq;
        }
        // 등록
        else {
          url = "/board-article/item";
        }
        delete this.item.attach;

        VueUtil.post(
          url,
          this.item,
          res => {
            this.$router.push({ name: "boardArticleList", query: this.$route.query });
          },
          { "call-type": "multipart" }
        );
      });
    },
    attachFile(event) {
      this.item.attachList = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.item.attachList.push(event.target.files[i]);
      }
    },
    encryptRun() {
      this.encryptProc(() => {
        this.$nextTick(() => {
          this.initEditor();
        });
      });
    }
  },
  mounted() {
    this.loadBoardManager();
    // 수정
    if (this.$route.query.boardArticleSeq) {
      VueUtil.get(`/board-article/item/${this.$route.query.boardArticleSeq}`, {}, res => {
        this.item = res.data;
        if (!this.item.encryptF) {
          this.initEditor();
        }
      });
    }
    // 등록
    else {
      this.initEditor();
    }
    window.openImageForm = a => {
      this.$refs["imageUpload"].open();
    };
  }
};
</script>
<style scoped></style>

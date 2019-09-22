<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('title')" name="title" data-vv-as="제목" placeholder="제목 넣어라"></b-form-input>
        <span v-show="!validateState('title')" class="invalid-feedback">{{ veeErrors.first('title') }}</span>
      </b-form-group>
      <b-form-group>
        <textarea v-model="item.content" id="content" rows="10" cols="100" style="width: 100%; height: 350px; display:none"></textarea>
      </b-form-group>
      <b-form-group>
        <b-form-file @change="attachFile($event)" :multiple="true" placeholder="첨부파일" />
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
import noteCommon from "./mixin-note.js";
import '../../asserts/lib/editor/js/HuskyEZCreator.js';
import impageUploadComponent from "../common/imageUpload/imageUpload.vue";
export default {
  mixins: [comFunction, noteCommon],
  components: {
    impageUploadComponent
  },
  data() {
    return {
      item: {
        title: "",
        content: "",
        attachList: [],
        categorySeq: 0,
      },
      oEditors: [],
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
          $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(e => {
            this.item.content = this.oEditors.getById["content"].getIR();
          });
          this.oEditors.getById["content"].setDefaultFont("나눔고딕", 10);
        },
      });
    },
    // 이미지 붙이기
    pasteImage(html) {
      this.oEditors.getById["content"].exec("PASTE_HTML", [html]);
    },
    submitProc() {
      let html = CommonUtil.clearHtml(this.item.content);
      if (CommonUtil.isEmpty(html)) {
        Swal.fire(
          '안내',
          '내용을 입력해',
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
        if (this.$route.query.noteSeq) {
          url = "/note/item-edit";
        }
        // 등록
        else {
          url = "/note/item";
          this.item.categorySeq = this.$route.query.categorySeq;
        }
        delete this.item.category;
        delete this.item.attach;
        VueUtil.post(url, this.item, (res) => {
          this.$router.push({ name: "noteList", query: this.$route.query });
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
    if (this.$route.query.noteSeq) {
      VueUtil.get(`/note/item/${this.$route.query.noteSeq}`, {}, (res) => {
        this.item = res.data;
        this.initEditor();
      });
    }
    // 등록
    else {
      this.initEditor();
    }
    window.openImageForm = (a) => {
      this.$refs['imageUpload'].open();
    }
  }
};
</script>
<style scoped>
</style>
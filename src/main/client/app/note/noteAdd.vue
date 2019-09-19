<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('item.title')" name="item.title" data-vv-as="제목" placeholder="제목 넣어라"></b-form-input>
        <span v-show="!validateState('item.title')" class="invalid-feedback">{{ veeErrors.first('item.title') }}</span>
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
          <b-button @click="getContent()" type="button" variant="info">getContent</b-button>
          <b-button @click="putContent()" type="button" variant="info">putContent</b-button>
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
        title: "BDAABB00",
        content: "우리집 강아지\n복슬강아지",
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
          // 내용 변경 감지
          $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(e => {
            console.log('e :', e);
          });
          this.oEditors.getById["content"].setDefaultFont("나눔고딕", 10);
        },
      });
    },
    // 이미지 붙이기
    pasteImage(html) {
      this.oEditors.getById["content"].exec("PASTE_HTML", [html]);
    },
    getContent() {
      let sHTML = this.oEditors.getById["content"].getIR();
      console.log('sHTML :', sHTML);
    },
    putContent() {
      let sHTML = "<span style='color:#FF0000;'>이미지도 같은 방식으로 삽입합니다.<\/span>";
      this.oEditors.getById["content"].exec("PASTE_HTML", [sHTML]);
    },
    submitProc() {
      this.item.categorySeq = this.$route.query.categorySeq;
      VueUtil.post("/note/item", this.item, (res) => {
        console.log('res :', res);
      }, { "call-type": "multipart" });
    },
    attachFile(event) {
      for (let i = 0; i < event.target.files.length; i++) {
        this.item.attachList.push(event.target.files[i]);
      }
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
<style scoped>
</style>
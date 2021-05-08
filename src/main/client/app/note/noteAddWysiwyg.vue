<template>
  <b-form-group>
    <textarea
      :value="value"
      id="content"
      rows="10"
      cols="100"
      style="width: 100%; height: 350px; display: none"
    ></textarea>
    <impageUploadComponent @pasted="pasteImage" ref="imageUpload" />
  </b-form-group>
</template>
<script>
import impageUploadComponent from "../common/imageUpload/imageUpload.vue";
import "../../asserts/lib/editor/js/HuskyEZCreator.js";

export default {
  data() {
    return {
      oEditors: [],
    };
  },
  props: {
    value: {
      type: String,
      require: true,
    }
  },
  components: {
    impageUploadComponent,
  },
  computed: {
  },
  mounted() {
    this.initEditor();
    window.openImageForm = a => {
      this.$refs["imageUpload"].open();
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
            .find("body")
            .keyup(e => {
              const val = this.oEditors.getById["content"].getIR();
              this.$emit('input', val);
            });
          this.oEditors.getById["content"].setDefaultFont("나눔고딕", 10);
        }
      });
    },
    // 이미지 붙이기
    pasteImage(html) {
      this.oEditors.getById["content"].exec("PASTE_HTML", [html]);
    },
  }
};
</script>
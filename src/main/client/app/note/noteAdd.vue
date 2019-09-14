<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('item.title')" name="item.title" data-vv-as="제목" placeholder="제목 넣어라"></b-form-input>
        <span v-show="!validateState('item.title')" class="invalid-feedback">{{ veeErrors.first('item.title') }}</span>
      </b-form-group>
      <b-form-group>
        <textarea id="content" rows="10" cols="100" style="width: 100%; height: 350px;">aaaaaaaaaaaaaa</textarea>
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
<script type="text/javascript" src="/asserts/editor/js/HuskyEZCreator.js"></script>
<script>
import noteCommon from "./mixin-note.js";
import CKEditor from '@ckeditor/ckeditor5-vue';
import '../../asserts/lib/editor/js/HuskyEZCreator.js';

// require styles
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
  mixins: [comFunction, noteCommon],
  components: {
    ckeditor: CKEditor.component
  },
  data() {
    return {
      editor: ClassicEditor,
      editorConfig: {
        // toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote', 'fullscreen', '|', 'undo', 'redo'],
      },
      item: {
        title: "BDAABB00",
        content: "우리집 강아지\n복슬강아지",
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
      oEditors: null,
    };
  },
  methods: {
    submitProc() {
      console.log("submit");
    },
    onEditorBlur(quill) {
      console.log('editor blur!', quill)
    },
    onEditorFocus(quill) {
      console.log('editor focus!', quill)
    },
    onEditorReady(quill) {
      console.log('editor ready!', quill)
    },
    onEditorChange({ quill, html, text }) {
      console.log('editor change!', quill, html, text)
      this.item.content = html
    }
  },
  mounted() {
    console.log('nhn.husky.EZCreator.createInIFrame :', nhn.husky.EZCreator.createInIFrame);

    nhn.husky.EZCreator.createInIFrame({
      oAppRef: this.oEditors,
      elPlaceHolder: "content",
      sSkinURI: "/asserts/editor/SmartEditor2Skin.html",
      fCreator: "createSEditorInIFrame",
      fOnAppLoad: function () {
        // 자동저장을 수정일 경우만 함.
        if ($scope.autoSave.run == false) {
          return;
        }
        // 내용 변경 감지
        $("iframe").contents().find('#se2_iframe').contents().find("body").keyup(
          function (e) {
            $scope.resetAutoSaveTimer();
          });
        $scope.runAutoSaveTimer();
      }
    });
  }
};
</script>
<style scoped>
</style>
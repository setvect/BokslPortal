<template>
  <div>
    <h5>등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <ckeditor :editor="editor" v-model="item.question" :config="editorConfig"></ckeditor>
      </b-form-group>
      <b-form-group>
        <ckeditor :editor="editor" v-model="item.answer" :config="editorConfig"></ckeditor>
      </b-form-group>
      <b-form-group>
        <b-form-file v-model="item.attach" :multiple="true" placeholder="첨부파일"/>
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
import CKEditor from '@ckeditor/ckeditor5-vue';

// require styles
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
  mixins: [comFunction, knowledgeCommon],
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
      }
    };
  },
  methods: {
    submitProc() {
      console.log("submit");
    },
  },
  mounted() {
    console.log('this is current quill instance object', this.editor)
  }
};
</script>
<style >
  .ck-editor__editable {
    min-height: 300px;
  }
</style>
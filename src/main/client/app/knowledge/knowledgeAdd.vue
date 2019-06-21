<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('item.title')" name="item.title" data-vv-as="제목" placeholder="제목 넣어라"></b-form-input>
        <span v-show="!validateState('item.title')" class="invalid-feedback">{{ veeErrors.first('item.title') }}</span>
      </b-form-group>
      <b-form-group>
        <quill-editor v-model="item.content" ref="myQuillEditor" :options="editorOption" @blur="onEditorBlur($event)" @focus="onEditorFocus($event)" @ready="onEditorReady($event)" style="height:300px; margin-bottom: 30px;" ></quill-editor>
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
import comFunction from "../commonFunction.js";
import knowledgeCommon from "./mixin-knowledge.js";
// require styles
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import { quillEditor } from 'vue-quill-editor'

export default {
  mixins: [comFunction, knowledgeCommon],
  components: {
    quillEditor
  },
  data() {
    return {
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
      editorOption: {
       placeholder: '내용을 입력하세요.',
      }
    };
  },
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill
    }
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
    console.log('this is current quill instance object', this.editor)
  }
};
</script>
<style scoped>
</style>
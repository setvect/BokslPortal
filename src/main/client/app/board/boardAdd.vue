<template>
  <div>
    <h5>게시판 만들기</h5>
    <form autocomplete="off">
      <b-form-group abel-cols="2" label-cols-lg="2" label="제목">
        <b-form-input v-model="item.title" v-validate="{ required: true, max: 100 }" :state="validateState('item.title')" name="item.title" data-vv-as="제목"></b-form-input>
        <span v-show="!validateState('item.title')" class="invalid-feedback">{{ veeErrors.first('item.title') }}</span>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="내용">
        <quill-editor v-model="item.content" ref="myQuillEditor" :options="editorOption" @blur="onEditorBlur($event)" @focus="onEditorFocus($event)" @ready="onEditorReady($event)" style="height:300px; margin-bottom: 30px;"></quill-editor>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="첨부파일">
        <b-form-file v-model="item.attach" :multiple="true"/>
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
import boardCommon from "./mixin-board.js";
// require styles
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import { quillEditor } from 'vue-quill-editor'

export default {
  mixins: [comFunction, boardCommon],
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
        // some quill options
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
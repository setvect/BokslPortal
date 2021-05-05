<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-input
          v-model="item.title"
          v-validate="{ required: true, max: 100 }"
          :state="validateState('title')"
          name="title"
          data-vv-as="제목"
          placeholder="제목 넣어라"
        ></b-form-input>
        <span v-show="!validateState('title')" class="invalid-feedback">{{
          veeErrors.first("title")
        }}</span>
      </b-form-group>
      <b-form-group>
        <b-form-select
          v-model="item.categorySeq"
          :state="validateState('categorySeq')"
          v-validate="{ required: true }"
          name="categorySeq"
          data-vv-as="분류"
          placeholder="분류 선택해라"
          size="sm"
        >
          <option :value="null">== 선택 ==</option>
          <option
            v-for="category in categoryList"
            :key="category.categorySeq"
            :value="category.categorySeq"
          >
            {{ category.name }}
          </option>
        </b-form-select>
        <span v-show="!validateState('categorySeq')" class="invalid-feedback">{{
          veeErrors.first("categorySeq")
        }}</span>
      </b-form-group>
      <b-form-group>
        <textarea
          v-model="item.content"
          id="content"
          rows="10"
          cols="100"
          style="width: 100%; height: 350px; display: none"
        ></textarea>
      </b-form-group>
      <b-form-group v-if="markdown">
        <div class="p-text-editor">
          <codemirror
            ref="editor"
            v-model="item.content"
            :options="cmOption"
          />
        </div>
      </b-form-group>
      <b-form-group>
        <b-form-file
          @change="attachFile($event)"
          :multiple="true"
          placeholder="첨부파일"
        />
      </b-form-group>
      <b-form-group>
        <ul>
          <li v-for="attach in item.attach" :key="attach.attachSeq">
            <b-check v-model="deleteAttachFileSeq" :value="attach.attachFileSeq"
              >{{ attach.originalName }} (size:
              {{ attach.size | numberFormat }} byte )</b-check
            >
          </li>
        </ul>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button @click="listPage()" type="button" variant="info"
            >취소</b-button
          >
        </b-col>
        <b-col>
          <span>{{ autoSave.label }}</span>
        </b-col>
        <b-col cols="auto">
          <b-button @click="submitProc()" type="button" variant="info"
            >확인</b-button
          >
        </b-col>
      </b-row>
    </form>
    <impageUploadComponent @pasted="pasteImage" ref="imageUpload" />
  </div>
</template>
<script>
import noteCommon from "./mixin-note.js";
import impageUploadComponent from "../common/imageUpload/imageUpload.vue";
import { codemirror } from 'vue-codemirror'
import "../../utils/vue-common.js";
import "../../asserts/lib/editor/js/HuskyEZCreator.js";

// base style
import 'codemirror/lib/codemirror.css'
// theme css
import 'codemirror/theme/eclipse.css'
import 'codemirror/theme/night.css'
// language
import 'codemirror/mode/vue/vue.js'
// active-line.js
import 'codemirror/addon/selection/active-line.js'
// styleSelectedText
import 'codemirror/addon/selection/mark-selection.js'
import 'codemirror/addon/search/searchcursor.js'
// highlightSelectionMatches
import 'codemirror/addon/scroll/annotatescrollbar.js'
import 'codemirror/addon/search/matchesonscrollbar.js'
import 'codemirror/addon/search/searchcursor.js'
import 'codemirror/addon/search/match-highlighter.js'
// keyMap
import 'codemirror/mode/clike/clike.js'
import 'codemirror/addon/edit/matchbrackets.js'
import 'codemirror/addon/comment/comment.js'
import 'codemirror/addon/dialog/dialog.js'
import 'codemirror/addon/dialog/dialog.css'
import 'codemirror/addon/search/searchcursor.js'
import 'codemirror/addon/search/search.js'
import 'codemirror/keymap/sublime.js'
// foldGutter
import 'codemirror/addon/fold/foldgutter.css'
import 'codemirror/addon/fold/brace-fold.js'
import 'codemirror/addon/fold/comment-fold.js'
import 'codemirror/addon/fold/foldcode.js'
import 'codemirror/addon/fold/foldgutter.js'
import 'codemirror/addon/fold/indent-fold.js'
import 'codemirror/addon/fold/markdown-fold.js'
import 'codemirror/addon/fold/xml-fold.js'

import 'codemirror/mode/markdown/markdown.js'

const DEFAULT_AUTO_SAVE_TIME = 15;
const INTERVAL_TIME = 3;
export default {
  mixins: [comFunction, noteCommon],
  components: {
    impageUploadComponent,
    codemirror
  },
  computed: {
  },
  data() {
    return {
      item: {
        title: "",
        content: "",
        attachList: [],
        categorySeq: 0,
        attach: []
      },
      deleteAttachFileSeq: [],
      oEditors: [],
      autoSave: {
        run: false,
        save: false,
        time: DEFAULT_AUTO_SAVE_TIME,
        label: ""
      },
      markdown: false,
      cmOption: {
        tabSize: 2,
        styleActiveLine: true,
        lineNumbers: true,
        line: true,
        foldGutter: true,
        styleSelectedText: true,
        mode: 'text/x-markdown',
        // keyMap: "sublime",
        matchBrackets: true,
        theme: "night",
        showCursorWhenSelecting: true,
        extraKeys: { "Ctrl": "autocomplete" },
        hintOptions: {
          completeSingle: false
        }
      },
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
              this.item.content = this.oEditors.getById["content"].getIR();
              this.resetAutoSaveTimer();
            });
          this.oEditors.getById["content"].setDefaultFont("나눔고딕", 10);
          // 자동저장을 수정일 경우만 함.
          if (this.autoSave.run == false) {
            return;
          }
          this.runAutoSaveTimer();
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
        if (this.$route.query.noteSeq) {
          url = "/note/item-edit";
          this.item.deleteAttachFileSeq = this.deleteAttachFileSeq;
        }
        // 등록
        else {
          url = "/note/item";
        }
        let copyItem = $.extend(true, {}, this.item);
        delete copyItem.category;
        delete copyItem.attach;

        VueUtil.post(
          url,
          copyItem,
          res => {
            this.$router.push({ name: "noteList", query: this.$route.query });
          },
          { "call-type": "multipart" }
        );
      });
    },
    // 자동 저장 리플래시
    resetAutoSaveTimer() {
      this.autoSave.time = DEFAULT_AUTO_SAVE_TIME;
      this.autoSave.save = false;
    },
    runAutoSaveTimer() {
      setInterval(() => {
        if (this.autoSave.time > 0) {
          this.autoSave.label = this.autoSave.time + "초 후 자동 저장";
          this.autoSave.time -= INTERVAL_TIME;
        } else {
          if (!this.autoSave.save) {
            this.autoSave.save = true;
            this.runAutoSave();
          }
        }
      }, INTERVAL_TIME * 1000);
    },
    runAutoSave() {
      VueUtil.post(
        "/note/item-edit",
        { noteSeq: this.item.noteSeq, title: this.item.title, content: this.item.content, categorySeq: this.item.categorySeq },
        res => {
          this.autoSave.label = "자동 저장 완료";
        },
        { "call-type": "multipart", wait: false }
      );
    },
    attachFile(event) {
      this.item.attachList = [];
      for (let i = 0; i < event.target.files.length; i++) {
        this.item.attachList.push(event.target.files[i]);
      }
    }
  },
  mounted() {
    this.item.categorySeq = null;
    if (this.$route.query.categorySeq && parseInt(this.$route.query.categorySeq) !== 0) {
      this.item.categorySeq = parseInt(this.$route.query.categorySeq);
    }

    // 수정
    if (this.$route.query.noteSeq) {
      VueUtil.get(`/note/item/${this.$route.query.noteSeq}`, {}, res => {
        this.item = res.data;
        this.markdown = this.item.markdown;
        this.initEditor();
        this.autoSave.run = true;
      });
    }
    // 등록
    else {
      console.log('this.$route.query.markdown :>> ', this.$route.query.markdown);
      this.markdown = this.$route.query.markdown;
      if (this.markdown) {
        // markdown
      } else {
        this.initEditor();
      }
    }
    this.loadCategory();
    window.openImageForm = a => {
      this.$refs["imageUpload"].open();
    };
  }
};
</script>
<style scoped></style>
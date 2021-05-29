<template>
  <b-form-group :class="{ fullscreen: fullscreen }">
    <b-row style="margin-left: 30px">
      <b-checkbox v-model="preview" @change="toggle($event)">
        미리보기
      </b-checkbox>
      <b-checkbox v-model="fullscreen" style="margin-left: 10px">
        전체화면
      </b-checkbox>
    </b-row>
    <b-row>
      <b-col :cols="editorWidth">
        <codemirror
          ref="editor"
          :value="value"
          :options="cmOption"
          @input="chnageInput($event)"
          @cursorActivity="changeCursor($event)"
          @ready="readyCodemirror"
        />
      </b-col>
      <b-col cols="6" v-if="preview">
        <MarkdownItVue
          class="preview"
          :style="{ height: editAreaHeight + 'px' }"
          :option="markdownItOption"
          :content="previewValue"
        />
      </b-col>
    </b-row>
  </b-form-group>
</template>
<script>
import cookies from 'js-cookie'
import { codemirror } from 'vue-codemirror'
import { getToken } from "../../utils/auth.js";
import "../../utils/vue-common.js";
import "inline-attachment/src/inline-attachment.js";
import "inline-attachment/src/codemirror-4.inline-attachment.js";

import "codemirror/addon/selection/active-line.js";
import "codemirror/addon/edit/continuelist.js";
import "codemirror/addon/scroll/simplescrollbars.js";
import "codemirror/addon/scroll/simplescrollbars.css";

import "codemirror/addon/search/searchcursor.js";
import "codemirror/addon/search/search.js";
import "codemirror/addon/search/matchesonscrollbar.js";

import "codemirror/mode/markdown/markdown.js";
import "codemirror/mode/meta.js";
import "codemirror/mode/javascript/javascript.js";
import "codemirror/mode/css/css.js";
import "codemirror/mode/sql/sql.js";
import "codemirror/mode/vue/vue.js";
import "codemirror/mode/shell/shell.js";
import "codemirror/mode/clike/clike.js";
import "codemirror/mode/gfm/gfm.js";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/eclipse.css";
import "codemirror/theme/night.css";
import "codemirror/addon/dialog/dialog.css";
import "codemirror/addon/search/matchesonscrollbar.css";


import MarkdownItVue from "markdown-it-vue";
import "markdown-it-vue/dist/markdown-it-vue.css";

export default {
  data() {
    return {
      cmOption: {
        tabSize: 2,
        lineNumbers: true,
        styleActiveLine: true,
        mode: 'gfm',
        theme: "default",
        scrollbarStyle: "simple",
        extraKeys: { "Enter": "newlineAndIndentContinueMarkdownList", "Alt-F": "findPersistent" }
      },
      markdownItOption: {
        markdownIt: {
          linkify: true
        },
        linkAttributes: {
          attrs: {
            target: '_blank',
            rel: 'noopener'
          }
        }
      },
      previewValue: "",
      preview: false,
      editAreaHeight: 100,
      fullscreen: false,
    };
  },
  props: {
    value: {
      type: String,
      require: true,
    }
  },
  watch: {
    fullscreen: {
      handler: function (newValue, oldValue) {
        if (this.fullscreen) {
          this.editAreaHeight = $(window).height() - 50;
          this.$refs.editor.codemirror.setSize(null, this.editAreaHeight);
        } else {
          this.editAreaHeight = Math.max($(window).height() - 450, 400);
          this.$refs.editor.codemirror.setSize(null, this.editAreaHeight);
        }
      }
    }
  },
  components: {
    codemirror,
    MarkdownItVue
  },
  computed: {
    editorWidth() {
      return this.preview ? 6 : 12;
    },
  },
  mounted() {
    inlineAttachment.defaults.uploadUrl = "/attach/uploadImage";
    inlineAttachment.defaults.extraHeaders["x-auth-token"] = getToken();
    this.preview = cookies.get("noteMarkdownPreview") === "true";
    this.editAreaHeight = Math.max($(window).height() - 450, 400);
    this.reiszeEditor();
    inlineAttachment.editors.codemirror4.attach(this.$refs.editor.codemirror);
    this.previewValue = this.value;
    this.previewRender();
    this.applyShortCut();
  },
  methods: {
    reiszeEditor() {
      this.$refs.editor.codemirror.setSize(null, this.editAreaHeight);
    },
    toggle(pre) {
      cookies.set("noteMarkdownPreview", pre, { expires: 365 });
    },
    chnageInput(value) {
      this.$emit('input', value);
    },
    changeCursor(editor) {
      const currentLine = editor.getCursor().line + 1;
      const selectElement = $(".preview").find(`[data-source-line=${currentLine}]`);
      if (selectElement.length) {
        const top = $(".preview").scrollTop() + selectElement.position().top;
        $(".preview").scrollTop(top - 150);
      }
    },
    readyCodemirror(cm) {
      // 빈 데이터로 undo 못하게 함
      cm.doc.clearHistory();
      cm.refresh();
    },
    // 키 입력마다 markdown preview를 반영하면 렉이 걸리는 문제를 해결하기 위해 1초 마다 반복적으로 값을 반영함
    previewRender() {
      const intervalId = setInterval(() => {
        if (!this.$route.path.endsWith("/add")) {
          console.log("clearInterval", intervalId);
          clearInterval(intervalId);
        }
        this.previewValue = this.value;
      }, 1000);
    },
    applyShortCut() {
      this.$refs.editor.codemirror.addKeyMap({
        // bold
        'Ctrl-B': function (cm) {
          var s = cm.getSelection(),
            t = s.slice(0, 2) === '**' && s.slice(-2) === '**';
          cm.replaceSelection(t ? s.slice(2, -2) : '**' + s + '**', 'around');
        },
        // italic
        'Ctrl-I': function (cm) {
          var s = cm.getSelection(),
            t = s.slice(0, 1) === '_' && s.slice(-1) === '_';
          cm.replaceSelection(t ? s.slice(1, -1) : '_' + s + '_', 'around');
        },
        // code
        'Ctrl-K': function (cm) {
          var s = cm.getSelection(),
            t = s.slice(0, 1) === '`' && s.slice(-1) === '`';
          cm.replaceSelection(t ? s.slice(1, -1) : '`' + s + '`', 'around');
        },
        // strike
        'Ctrl-Delete': function (cm) {
          var s = cm.getSelection(),
            t = s.slice(0, 2) === '~~' && s.slice(-2) === '~~';
          if (!s) return null;
          cm.replaceSelection(t ? s.slice(2, -2) : '~~' + s + '~~', 'around');
        }
      });
    }
  }
};
</script>

<style scoped>
.fullscreen {
  z-index: 100;
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background: white;
}
</style>

<style>
.CodeMirror {
  font-family: D2Coding ligature, D2Coding, NanumGothicCoding, Arial, monospace;
  font-size: 1.3em;
}
.preview{
  overflow: auto;
}
</style>


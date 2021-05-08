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
          @input="chnage($event)"
        />
      </b-col>
      <b-col cols="6" v-show="preview">
        <MarkdownItVue
          class="preview"
          :style="{ height: editAreaHeight + 'px' }"
          :option="markdownItOption"
          :content="value"
        />
      </b-col>
    </b-row>
  </b-form-group>
</template>
<script>
import cookies from 'js-cookie'
import { codemirror } from 'vue-codemirror'
import "../../utils/vue-common.js";

import "codemirror/addon/selection/active-line.js";
import "codemirror/addon/edit/continuelist.js";
import "codemirror/addon/scroll/simplescrollbars.js";
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
import "codemirror/addon/scroll/simplescrollbars.css";

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
        extraKeys: { "Enter": "newlineAndIndentContinueMarkdownList" }
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
    this.preview = cookies.get("noteMarkdownPreview") === "true";
    this.editAreaHeight = Math.max($(window).height() - 450, 400);
    this.reiszeEditor();
  },
  methods: {
    reiszeEditor() {
      this.$refs.editor.codemirror.setSize(null, this.editAreaHeight);
    },
    toggle(pre) {
      cookies.set("noteMarkdownPreview", pre, { expires: 365 });
    },
    chnage(value) {
      this.$emit('input', value);
    },
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

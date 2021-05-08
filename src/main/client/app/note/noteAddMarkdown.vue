<template>
  <b-form-group>
    <b-row style="margin-left: 30px">
      <b-checkbox v-model="preview" @change="toggle($event)"
        >미리보기</b-checkbox
      >
      <b-checkbox style="margin-left: 10px">전체화면</b-checkbox>
    </b-row>
    <b-row>
      <b-col>
        <codemirror
          ref="editor"
          :value="value"
          :options="cmOption"
          @input="chnage($event)"
        />
      </b-col>
      <b-col>ㅋㅋㅋ</b-col>
    </b-row>
  </b-form-group>
</template>
<script>
import cookies from 'js-cookie'
import { codemirror } from 'vue-codemirror'
import "../../utils/vue-common.js";

// base style
import 'codemirror/lib/codemirror.css'
// theme css
import 'codemirror/theme/eclipse.css'
import 'codemirror/theme/night.css'
// active-line.js
import 'codemirror/addon/selection/active-line.js'

import 'codemirror/addon/edit/continuelist.js'
import 'codemirror/mode/markdown/markdown.js'
import 'codemirror/mode/meta.js'
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/css/css.js'
import 'codemirror/mode/sql/sql.js'
import 'codemirror/mode/vue/vue.js'
import 'codemirror/mode/shell/shell.js'
import 'codemirror/mode/clike/clike.js'
import 'codemirror/mode/gfm/gfm.js'

export default {
  data() {
    return {
      cmOption: {
        tabSize: 2,
        lineNumbers: true,
        styleActiveLine: true,
        mode: 'gfm',
        theme: "default",
        lineWrapping: true,
        extraKeys: { "Enter": "newlineAndIndentContinueMarkdownList" }
      },
      preview: true
    };
  },
  props: {
    value: {
      type: String,
      require: true,
    }
  },
  components: {
    codemirror
  },
  computed: {
  },
  mounted() {
    this.preview = cookies.get("noteMarkdownPreview") || false;
  },
  methods: {
    toggle(pre) {
      console.log('pre :>> ', pre);
      cookies.set("noteMarkdownPreview", pre, { expires: 365 });
    },
    chnage(value) {
      console.log('value :>> ', value);
      this.$emit('input', value);
    }
  }
};
</script>
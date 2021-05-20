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
      <noteAddWysiwyg v-if="!markdown" v-model="item.content" />
      <noteMarkdown v-if="markdown" v-model="item.content" />
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
  </div>
</template>
<script>
import noteCommon from "./mixin-note.js";
import noteMarkdown from "./noteAddMarkdown.vue";
import noteAddWysiwyg from "./noteAddWysiwyg.vue";
import "../../utils/vue-common.js";
import "../../asserts/lib/editor/js/HuskyEZCreator.js";

const DEFAULT_AUTO_SAVE_TIME = 15;
const INTERVAL_TIME = 3;
export default {
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
      autoSave: {
        run: false,
        save: true,
        time: DEFAULT_AUTO_SAVE_TIME,
        label: ""
      },
      markdown: false,
    };
  },
  mixins: [comFunction, noteCommon],
  components: {
    noteAddWysiwyg,
    noteMarkdown,
  },
  computed: {
  },
  watch: {
    item: {
      deep: true,
      handler: function (newData, oldData) {
        if (!oldData.content) {
          return;
        }
        this.resetAutoSaveTimer();
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
        this.markdown = this.item.markdownF;
        this.autoSave.run = true;
        this.runAutoSaveTimer();
      });
    }
    // 등록
    else {
      this.markdown = this.$route.query.markdown;
    }
    this.loadCategory();
    window.openImageForm = a => {
      this.$refs["imageUpload"].open();
    };
  },
  methods: {
    submitProc() {
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
        copyItem.markdownF = this.markdown;
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
        if (this.autoSave.save) {
          return;
        }

        if (this.autoSave.time > 0) {
          this.autoSave.label = this.autoSave.time + "초 후 자동 저장";
          this.autoSave.time -= INTERVAL_TIME;
        } else {

          this.autoSave.save = true;
          this.runAutoSave();
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
    },
  }
};
</script>
<style scoped></style>
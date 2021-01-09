<template>
  <div>
    <h5>게시판 만들기</h5>
    <form autocomplete="off">
      <b-form-group abel-cols="2" label-cols-lg="2" label="코드">
        <b-form-input v-model="item.boardCode" v-validate="{ required: true, length: 8 }" :disabled="isEditMode" :state="validateState('boardCode')" name="boardCode" data-vv-as="코드"></b-form-input>
        <span v-show="!validateState('boardCode')" class="invalid-feedback">{{ veeErrors.first('boardCode') }}</span>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="이름">
        <b-form-input v-model="item.name" v-validate="{ required: true, max: 20 }" :state="validateState('name')" name="name" data-vv-as="이름"></b-form-input>
        <span v-show="!validateState('name')" class="invalid-feedback">{{ veeErrors.first('name') }}</span>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="업로드 용량제한">
        <b-form-input v-model="item.uploadLimit" v-validate="{ required: true, max: 8, integer: true}" :state="validateState('uploadLimit')" name="uploadLimit" data-vv-as="업로드 용량제한"></b-form-input>
        <span v-show="!validateState('uploadLimit')" class="invalid-feedback">{{ veeErrors.first('uploadLimit') }}</span>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="댓글 사용">
        <b-form-radio-group v-model="item.commentF" name="commentF">
          <b-form-radio value="true">예</b-form-radio>
          <b-form-radio value="false">아니오</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="파일 업로드">
        <b-form-radio-group v-model="item.attachF" name="attachF">
          <b-form-radio value="true">예</b-form-radio>
          <b-form-radio value="false">아니오</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="암호화 글 등록">
        <b-form-radio-group v-model="item.encryptF" name="encryptF">
          <b-form-radio value="true">예</b-form-radio>
          <b-form-radio value="false">아니오</b-form-radio>
        </b-form-radio-group>
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
import boardCommon from './mixin-boardManager.js'

export default {
  mixins: [comFunction, boardCommon],
  data() {
    return {
      item: {
        boardCode: "",
        name: '',
        uploadLimit: 1000,
        commentF: false,
        attachF: true,
        encodeF: false
      }
    }
  },
  computed: {
    isEditMode(){
      return this.$route.query.boardCode != null;
    }
  },
  methods: {
    submitProc() {
      this.$validator.validateAll().then((result) => {
        if (!result) {
          return;
        }
        let url = "/board-manager/item";
        let ajaxFunction;
        // boardCode 파라미터 여부에 따라 수정, 등록 선택
        if (this.$route.query.boardCode) {
          ajaxFunction = VueUtil.put;
        } else {
          ajaxFunction = VueUtil.post;
        }

        ajaxFunction(url, this.item, (res) => {
          this.listPage();
        });
      });
    },
  },
  mounted() {
    // 수정
    if (this.$route.query.boardCode) {
      VueUtil.get(`/board-manager/item/${this.$route.query.boardCode}`, {}, (res) => {
        this.item = res.data;
      });
    }
  }
}
</script>
<style scoped>
</style>

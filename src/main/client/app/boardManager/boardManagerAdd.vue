<template>
  <div>
    <h5>게시판 만들기</h5>
    <form autocomplete="off">
      <b-form-group abel-cols="2" label-cols-lg="2" label="코드">
        <b-form-input v-model="item.boardCode" v-validate="{ required: true, length: 8 }" :state="validateState('item.boardCode')" name="item.boardCode" data-vv-as="코드"></b-form-input>
        <span v-show="!validateState('item.boardCode')" class="invalid-feedback">{{ veeErrors.first('item.boardCode') }}</span>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="이름">
        <b-form-input v-model="item.name" v-validate="{ required: true, max: 20 }" :state="validateState('item.name')" name="item.name" data-vv-as="이름"></b-form-input>
        <span v-show="!validateState('item.name')" class="invalid-feedback">{{ veeErrors.first('item.name') }}</span>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="업로드 용량제한">
        <b-form-input v-model="item.uploadLimit" v-validate="{ required: true, max: 8, integer: true}" :state="validateState('item.uploadLimit')" name="item.uploadLimit" data-vv-as="업로드 용량제한"></b-form-input>
        <span v-show="!validateState('item.uploadLimit')" class="invalid-feedback">{{ veeErrors.first('item.uploadLimit') }}</span>
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
        <b-form-radio-group v-model="item.encodeF" name="encodeF">
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
import comFunction from '../commonFunction.js'
import boardCommon from './mixin-boardManager.js'

export default {
  mixins: [comFunction, boardCommon],
  data() {
    return {
      item: {
        boardCode: "BDAABB00",
        name: '공지사항',
        uploadLimit: 1000,
        commentF: false,
        attachF: true,
        encodeF: false
      }
    }
  }, computed: {
    validation() {
      return this.item.boardCode.length > 4 && this.item.boardCode.length < 13
    }
  },
  methods: {
    submitProc() {
      console.log("submit")
    },
  }
}
</script>
<style scoped>
</style>
<template>
  <div>
    <h5>게시판 만들기</h5>
    <form>
      <b-form-group label-cols="2" label-cols-lg="2" label="코드">
        <b-input v-model="item.boardCode" :state="validation"/>
        <b-form-invalid-feedback :state="validation">Your user ID must be 5-12 characters long.</b-form-invalid-feedback>
        <b-form-valid-feedback :state="validation">Looks Good.</b-form-valid-feedback>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="이름">
        <b-input/>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="업로드 용량제한">
        <b-input/>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="댓글 사용">
        <b-input/>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="파일 업로드">
        <b-input/>
      </b-form-group>
      <b-form-group label-cols="2" label-cols-lg="2" label="암호화 글 등록">
        <b-input/>
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
import { validationMixin } from 'vuelidate'
import { required, minLength } from 'vuelidate/lib/validators'

export default {
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
      console.log('validationMixin :', validationMixin);
      console.log("수정")
    },
    listPage() {
      this.$router.push({ name: 'boardManagerList' })
    },
  }
}
</script>
<style scoped>
</style>
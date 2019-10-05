<template>
  <div>
    <h5>암호화</h5>
    <form autocomplete="off">
      <b-form-group abel-cols="2" label-cols-lg="2" label="코드">
        <b-form-input v-model="item.boardCode" v:state="validateState('boardCode')" name="boardCode" data-vv-as="코드"></b-form-input>
        <span v-show="!validateState('boardCode')" class="invalid-feedback">{{ veeErrors.first('boardCode') }}</span>
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
import boardCommon from './mixin-boardArticle.js'

export default {
  mixins: [comFunction, boardCommon],
  data() {
    return {
      encrypt:""
    }
  },
  methods: {
    submitProc() {

    },
  },
  mounted() {
  }
}
</script>
<style scoped>
</style>
<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-textarea v-model="item.content" v-validate="{ required: true}" :state="validateState('item.content')" name="item.content" data-vv-as="내용" placeholder="내용 넣어라" style="height:300px;"></b-form-textarea>
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
import comFunction from "../commonFunction.js";


export default {
  mixins: [comFunction],
  data() {
    return {
      item: {
        content: "우리집 강아지\n복슬강아지",
      },
    };
  },
  methods: {
    submitProc() {
      this.$validator.validate().then((result) => {
        if (result) {
          alert('Form Submitted!');
          return;
        }
        alert('Correct them errors!');
      });
    },
  },
  mounted() {
    console.log('VueBootstrapDatetimePicker :', VueBootstrapDatetimePicker);
  }
};
</script>
<style scoped>
</style>
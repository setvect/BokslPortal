<template>
  <div>
    <h5>후보 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-textarea v-model="target" v-validate="{ required: true}" :state="validateState('target')" name="target" data-vv-as="내용" placeholder="내용 넣어라" style="height:200px;"></b-form-textarea>
        <span v-show="!validateState('target')" class="invalid-feedback">{{ veeErrors.first('target') }}</span>
      </b-form-group>
      <b-row>
        <b-col cols="auto">
          <b-button @click="next()" type="button" variant="info">다음</b-button>
        </b-col>
      </b-row>
    </form>
  </div>
</template>
<script>
import comFunction from "../../commonFunction.js";

export default {
  mixins: [comFunction],
  data() {
    return {
      target: ""
    };
  },
  methods: {
    next() {
      this.$validator.validate().then((result) => {
        if (result) {
          return;
        }
        this.$router.push({ name: "select-step2" });
      });
    }
  },
  mounted() {
  }
};
</script>
<style scoped>
</style>
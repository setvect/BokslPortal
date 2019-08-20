<template>
  <div>
    <h5>후보 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <b-form-textarea v-model="content" v-validate="{ required: true}" :state="validateState('content')" name="content" data-vv-as="내용" placeholder="내용 넣어라" style="height:200px;"></b-form-textarea>
        <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
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
import cookies from 'js-cookie'

export default {
  mixins: [comFunction],
  data() {
    return {
      content: ""
    };
  },
  methods: {
    next() {
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }
        // expires 100 days
        cookies.set("BokslSelectValue", this.content, { expires: 100 });
        this.$router.push({ name: "select-step2" });
      });
    }
  },
  mounted() {
    this.content = cookies.get("BokslSelectValue");
  }
};
</script>
<style scoped>
</style>
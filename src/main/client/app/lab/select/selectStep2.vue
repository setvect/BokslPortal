<template>
  <div>
    <h5>선택의 시간</h5>
    <form autocomplete="off">
      <b-row>
        <b-col>
          <b-button @click="run()" type="button" variant="info">돌리기</b-button>
          <b-button @click="back()" type="button" variant="info" style="float:right">돌아가기</b-button>
        </b-col>
      </b-row>
    </form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      target: ""
    };
  },
  methods: {
    run() {
      console.log("run...");
    },
    back() {
      this.$router.push({ name: "select-step1" });
    }
  },
  mounted() {
  }
};
</script>
<style scoped>
</style>
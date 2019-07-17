<template>
  <div>
    <h5>내 운명을 바꿀 숫자.</h5>
    <table class="table table-dark">
      <tbody>
        <tr v-for="(numbers, idx) in luckNumber" :key="idx">
          <td v-for="number in numbers" :key="number">{{number}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import VueUtil from "../../../utils/vue-util.js"

export default {
  data() {
    return {
      luckNumber: [
      ]
    }
  },
  methods: {
    init() {
      VueUtil.get(
        "/lab/lotto/luck.json",
        {},
        result => {
          this.luckNumber = result.data;
        },
      )
    }
  },
  mounted() {
    console.log('this.$store.getters.token :', this.$store.getters.token);
    this.init();
  },

};
</script>

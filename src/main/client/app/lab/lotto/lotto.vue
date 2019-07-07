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
export default {
  data() {
    return {
      luckNumber: [
        [1,2,3,4,5,6],
        [10,11,12,13,14,15],
      ]
    }
  },
  methods: {
  },
  mounted() {
  }
};
</script>

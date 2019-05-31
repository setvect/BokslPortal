<template>
  <div>
    <h1>ㅋㅋㅋ</h1>
    <b-alert variant="success" show>Success Alert</b-alert>
    <div class="dashboard-text">name:{{ name }}</div>
    <div class="dashboard-text">
      roles:
      <span v-for="role in roles" :key="role">{{ role }}</span>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }, mounted() {
  }
}
</script>
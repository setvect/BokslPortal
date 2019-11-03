<template>
  <div>
    <h1>ㅋㅋㅋ</h1>
    <b-alert variant="success" show>Success Alert</b-alert>
    <div class="dashboard-text">name:{{ name }}</div>
    <div class="dashboard-text">
      roles:
      <span v-for="role in roles" :key="role">{{ role }}</span>
    </div>
    <div>
      <comment moduleName="MAIN" moduleId="1"/>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import commentComponent from "../app/comment/comment.vue";

export default {
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  components:{
    comment: commentComponent
  },
  mounted() {
  }
}
</script>
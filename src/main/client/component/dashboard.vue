<template>
  <div>
    <h1>당장 행복하자</h1>
    <div>
      <comment moduleName="MAIN" moduleId="1"/>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import commentComponent from "../app/comment/comment.vue";

export default {
  components:{
    comment: commentComponent
  },
  mounted() {
  }
}
</script>
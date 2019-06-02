<template>
  <div>
    <!-- Font Awesome -->
    <link href="../vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <router-view/>
  </div>
</template>
<script type="text/javascript">
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'
import menuComponent from './menu.vue'
import topComponent from './top.vue'
import '../css/custom.css'

export default {
  data() {
    return {}
  },
  methods: {
  },
  mounted() {
  }
}
</script>


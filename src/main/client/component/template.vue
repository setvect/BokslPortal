<template>
  <div>
    <!-- Font Awesome -->
    <link href="../vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

    <router-view/>
  </div>
</template>
<script type="text/javascript">
import menuComponent from './menu.vue'
import topComponent from './top.vue'
import '../css/custom.css'
// import 'font-awesome/css/font-awesome.css'

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


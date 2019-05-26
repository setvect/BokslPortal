<template>
  <div class="container body">
    <div class="main_container">
      <div class="col-md-3 left_col">
        <div class="left_col scroll-view">
          <div class="navbar nav_title" style="border: 0;">
            <a href="index.html" class="site_title">
              <i class="fa fa-paw"></i>
              <router-link to="/"><span style="color:white">복슬포털</span></router-link>
            </a>
          </div>

          <div class="clearfix"></div>
          <!-- sidebar menu -->
          <side-menu/>
          <!-- /sidebar menu -->
        </div>
      </div>

      <!-- top navigation -->
      <top/>
      <!-- /top navigation -->

      <!-- page content -->
      <div class="right_col">
        <router-view/>
      </div>
      <!-- /page content -->
    </div>
  </div>
</template>
<script type="text/javascript">
import pageBody from '../app/custom.js'
import menuComponent from './menu.vue'
import topComponent from './top.vue'

export default {
  data() {
    return {
      items: [
        { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
        { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
        { age: 89, first_name: 'Geneva', last_name: 'Wilson' },
        { age: 38, first_name: 'Jami', last_name: 'Carney' }
      ]
    }
  },
  components: {
    sideMenu: menuComponent,
    top: topComponent
  },
  methods: {
  },
  mounted() {
    pageBody.init();
  }
}
</script>


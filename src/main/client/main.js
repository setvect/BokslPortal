import Vue from 'vue'
import template from './component/template.vue'
import BootstrapVue from 'bootstrap-vue'
import router from "./router.js"
import store from './store/index'

import './assert/font-awesome/css/font-awesome.css'
import './css/custom.css'
import './permission.js'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)

new Vue({
  el: '#app',
  render: h => h(template),
  router,
  store,
})

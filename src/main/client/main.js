import Vue from 'vue'
import template from './component/template.vue'
import BootstrapVue from 'bootstrap-vue'
import router from "./router.js"
import store from './store/index'
import './permission.js'

console.log('router :', router);
Vue.use(BootstrapVue)

new Vue({
  el: '#app',
  render: h => h(template),
  router,
  store,
})

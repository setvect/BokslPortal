import Vue from 'vue'
import template from './component/template.vue'
import BootstrapVue from 'bootstrap-vue'
import router from "./router.js"
import store from './store/index'
import './permission.js'
// import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


console.log('router :', router);
Vue.use(BootstrapVue)

new Vue({
  el: '#app',
  render: h => h(template),
  router,
  store,
})

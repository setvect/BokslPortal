import Vue from 'vue'
import App from './component/main'
import BootstrapVue from 'bootstrap-vue'
import router from "./rounter"
console.log('router :', router);
Vue.use(BootstrapVue)

new Vue({
  el: '#app',
  render: h => h(App),
  router,
})

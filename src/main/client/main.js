import Vue from 'vue'
import App from './component/main'
import BootstrapVue from 'bootstrap-vue'

Vue.use(BootstrapVue)

new Vue({
  el: '#app',
  render: h => h(App),
  mounted(){
    console.log("###############");
  }
})

import Vue from 'vue'
import App from './component/main'

new Vue({
  el: '#app',
  render: h => h(App),
  mounted(){
    console.log("###############");
  }
})

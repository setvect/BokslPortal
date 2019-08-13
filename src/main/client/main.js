import Vue from "vue"
import template from "./component/template.vue"
import BootstrapVue from "bootstrap-vue"
import router from "./router.js"
import store from "./store/index"

import "./assert/font-awesome/css/font-awesome.css"
import "./css/custom.css"
import "./permission.js"
import "bootstrap/dist/css/bootstrap.css"
import "bootstrap-vue/dist/bootstrap-vue.css"
import VeeValidate from "vee-validate"
import ko from "vee-validate/dist/locale/ko"

import VueLazyLoad from 'vue-lazyload'
import Notifications from 'vue-notification'
import VueLoading from 'vue-loading-overlay'

Vue.use(BootstrapVue);
Vue.use(VueLazyLoad);
Vue.use(VeeValidate, {
  inject: true,
  locale: "ko",
  dictionary: {
    ko
  },
  fieldsBagName: "veeFields",
  errorBagName: "veeErrors"
});
Vue.use(Notifications);
Vue.use(VueLoading);

new Vue({
  el: "#app",
  render: h => h(template),
  router,
  store
});

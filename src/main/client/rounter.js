import Vue from "vue"
import Router from "vue-router"
import deshboard from "./component/dashboard.vue"

Vue.use(Router)
export const constantRouterMap = [
  {
    path: "/",
    component: deshboard,
    name: "Dashboard",
  }
]

export default new Router({
  // mode: 'history',
  routes: constantRouterMap
})

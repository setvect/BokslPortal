import Vue from "vue"
import Router from "vue-router"

Vue.use(Router)
export const constantRouterMap = [
  {
    path: "/login",
    component: () => import("./component/login.vue"),
    name: "login"
  },
  {
    path: "/main",
    component: () => import("./component/main.vue"),
    name: "main",
    children:[
      {
        path: "dashboard",
        component: () => import("./component/dashboard.vue"),
        name: "dashboard"
      }
    ]
  }
]

export default new Router({
  // mode: 'history',
  routes: constantRouterMap
})

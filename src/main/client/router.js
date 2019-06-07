import Vue from "vue"
import Router from "vue-router"

Vue.use(Router)
export const constantRouterMap = [
  { path: "/404", component: () => import("./views/404"), hidden: true },
  {
    path: "/",
    redirect: "/main/dashboard"
  },
  {
    path: "/login",
    component: () => import("./component/login.vue"),
    name: "login"
  },
  {
    path: "/main",
    component: () => import("./component/main.vue"),
    name: "main",
    children: [
      {
        path: "dashboard",
        component: () => import("./component/dashboard.vue"),
        name: "dashboard"
      },
      {
        path: "boardManager",
        component: () => import("./app/boardManager/index.vue"),
        name: "boardManager",
        redirect: "/main/boardManager/list",
        children: [
          {
            path: "list",
            component: () => import("./app/boardManager/boardManagerList.vue"),
            name: "boardManagerList"
          },
          {
            path: "read",
            component: () => import("./app/boardManager/boardManagerRead.vue"),
            name: "boardManagerRead"
          }
        ]
      },
      {
        path: "code",
        component: () => import("./app/code/code.vue"),
        name: "code"
      }
    ]
  },
  { path: "*", redirect: "/404" }
]

export default new Router({
  // mode: 'history',
  routes: constantRouterMap
})

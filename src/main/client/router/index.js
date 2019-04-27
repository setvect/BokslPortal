import Vue from "vue"
import Router from "vue-router"

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from "../views/layout/Layout"

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: "/login", component: () => import("@/views/login/index"), hidden: true },
  { path: "/404", component: () => import("@/views/404"), hidden: true },

  {
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    name: "Dashboard",
    hidden: true,
    children: [
      {
        path: "dashboard",
        component: () => import("@/views/dashboard/index")
      }
    ]
  },
  {
    path: "/home",
    component: Layout,
    redirect: "/home/main",
    name: "home",
    meta: { title: "홈피", icon: "home" },
    children: [
      {
        path: "home",
        name: "Table",
        component: () => import("@/views/table/index"),
        meta: { title: "홈" }
      },
      {
        path: "manager",
        name: "Table",
        component: () => import("@/views/table/index"),
        meta: { title: "관리" }
      }
    ]
  },
  {
    path: "/note",
    component: Layout,
    children: [
      {
        meta: { title: "복슬노트", icon: "edit" },
        path: "index",
        name: "Form",
        component: () => import("@/views/form/index")
      }
    ]
  },
  {
    path: "/knowledge",
    component: Layout,
    children: [
      {
        meta: { title: "복슬지식", icon: "nested" },
        path: "index",
        name: "Form",
        component: () => import("@/views/form/index")
      }
    ]
  },
  {
    path: "/memo",
    component: Layout,
    children: [
      {
        meta: { title: "복슬메모", icon: "clipboard" },
        path: "index",
        name: "Form",
        component: () => import("@/views/form/index")
      }
    ]
  },
  {
    path: "/network",
    component: Layout,
    children: [
      {
        meta: { title: "복슬관계", icon: "tree" },
        path: "index",
        name: "Form",
        component: () => import("@/views/form/index")
      }
    ]
  },
  {
    path: "/photo",
    component: Layout,
    children: [
      {
        meta: { title: "복슬포토", icon: "peoples" },
        path: "index",
        name: "Form",
        component: () => import("@/views/form/index")
      }
    ]
  },
  {
    path: "/experiment",
    component: Layout,
    meta: { title: "실험실", icon: "chart" },
    children: [
      {
        meta: { title: "로또" },
        path: "lotto",
        name: "Form",
        component: () => import("@/views/form/index")
      },
      {
        meta: { title: "결정 장애 해결" },
        path: "choice",
        name: "Form",
        component: () => import("@/views/form/index")
      }
    ]
  },
  { path: "*", redirect: "/404", hidden: true }
]

export default new Router({
  // mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

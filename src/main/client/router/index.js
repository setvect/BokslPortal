import Vue from "vue"
import Router from "vue-router"
Vue.use(Router)

/* Layout */
import Layout from "../views/layout/Layout"
import boardManagerRouter from "./modules/boardManager"

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
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
        name: "home-main",
        component: () => import("@/views/home/main"),
        meta: { title: "홈" }
      },
      boardManagerRouter,
      {
        path: "board",
        name: "board",
        component: () => import("@/views/home/board"),
        redirect: "/home/boardManager/boardList",
        meta: { title: "게시판" },
        children: [
          {
            path: "list",
            name: "board-list",
            component: () => import("@/views/home/board/boardList"),
            hidden: true,
            meta: { title: "목록" }
          },
          {
            path: "read",
            name: "board-read",
            component: () => import("@/views/home/board/boardRead"),
            hidden: true,
            meta: { title: "읽기" }
          },
          {
            path: "write",
            name: "board-write",
            component: () => import("@/views/home/board/boardWrite"),
            hidden: true,
            meta: { title: "쓰기" }
          }
        ]
      },
      {
        path: "code",
        name: "code",
        component: () => import("@/views/home/code/code"),
        meta: { title: "코드관리" }
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
        name: "note",
        component: () => import("@/views/note/note")
      }
    ]
  },
  {
    path: "/knowledge",
    component: Layout,
    children: [
      {
        meta: { title: "복슬지식", icon: "book" },
        path: "index",
        name: "knowledge",
        component: () => import("@/views/knowledge/knowledge")
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
        name: "memo",
        component: () => import("@/views/memo/memo")
      }
    ]
  },
  {
    path: "/network",
    component: Layout,
    children: [
      {
        meta: { title: "복슬관계", icon: "sitemap" },
        path: "index",
        name: "relation",
        component: () => import("@/views/relation/relation")
      }
    ]
  },
  {
    path: "/photo",
    component: Layout,
    children: [
      {
        meta: { title: "복슬포토", icon: "camera" },
        path: "index",
        name: "photo",
        component: () => import("@/views/photo/photo")
      }
    ]
  },
  {
    path: "/task",
    component: Layout,
    children: [
      {
        meta: { title: "복슬할일", icon: "tasks" },
        path: "index",
        name: "task",
        component: () => import("@/views/todo/todo")
      }
    ]
  },
  {
    path: "/lab",
    component: Layout,
    meta: { title: "실험실", icon: "lightbulb-o" },
    children: [
      {
        meta: { title: "로또" },
        path: "lotto",
        name: "lotto",
        component: () => import("@/views/lab/lotto/lotto")
      },
      {
        meta: { title: "결정 장애 해결" },
        path: "choice",
        name: "choice",
        component: () => import("@/views/lab/choice/choice")
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

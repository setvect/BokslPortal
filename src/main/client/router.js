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
        component: () => import("./app/index.vue"),
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
          },
          {
            path: "add",
            component: () => import("./app/boardManager/boardManagerAdd.vue"),
            name: "boardManagerAdd"
          }
        ]
      },
      {
        path: "board",
        component: () => import("./app/index.vue"),
        name: "board",
        redirect: "/main/board/list",
        children: [
          {
            path: "list",
            component: () => import("./app/board/boardList.vue"),
            name: "boardList"
          },
          {
            path: "read",
            component: () => import("./app/board/boardRead.vue"),
            name: "boardRead"
          },
          {
            path: "add",
            component: () => import("./app/board/boardAdd.vue"),
            name: "boardAdd"
          }
        ]
      },
      {
        path: "code",
        component: () => import("./app/code/code.vue"),
        name: "code"
      },
      {
        path: "knowledge",
        component: () => import("./app/index.vue"),
        name: "knowledge",
        redirect: "/main/knowledge/list",
        children: [
          {
            path: "list",
            component: () => import("./app/knowledge/knowledgeList.vue"),
            name: "knowledgeList"
          },
          {
            path: "read",
            component: () => import("./app/knowledge/knowledgeRead.vue"),
            name: "knowledgeRead"
          },
          {
            path: "add",
            component: () => import("./app/knowledge/knowledgeAdd.vue"),
            name: "knowledgeAdd"
          }
        ]
      },
      {
        path: "note",
        component: () => import("./app/index.vue"),
        name: "note",
        redirect: "/main/note/list",
        children: [
          {
            path: "list",
            component: () => import("./app/note/noteList.vue"),
            name: "noteList"
          },
          {
            path: "read",
            component: () => import("./app/note/noteRead.vue"),
            name: "noteRead"
          },
          {
            path: "add",
            component: () => import("./app/note/noteAdd.vue"),
            name: "noteAdd"
          }
        ]
      },
    ]
  },
  { path: "*", redirect: "/404" }
]

export default new Router({
  // mode: 'history',
  routes: constantRouterMap
})

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
        path: "boardArticle",
        component: () => import("./app/index.vue"),
        name: "boardArticle",
        redirect: "/main/boardArticle/list",
        children: [
          {
            path: "list",
            component: () => import("./app/boardArticle/boardArticleList.vue"),
            name: "boardArticleList"
          },
          {
            path: "read",
            component: () => import("./app/boardArticle/boardArticleRead.vue"),
            name: "boardArticleRead"
          },
          {
            path: "add",
            component: () => import("./app/boardArticle/boardArticleAdd.vue"),
            name: "boardArticleAdd"
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
      {
        path: "memo",
        component: () => import("./app/index.vue"),
        name: "memo",
        redirect: "/main/memo/memoList",
        children: [
          {
            path: "memoList",
            component: () => import("./app/memo/memoList.vue"),
            name: "memoList"
          },
          {
            path: "memoAdd",
            component: () => import("./app/memo/memoAdd.vue"),
            name: "memoAdd"
          },
          {
            path: "memoLayer",
            component: () => import("./app/memo/memoLayer.vue"),
            name: "memoLayer"
          }
        ]
      },
      {
        path: "photo",
        component: () => import("./app/index.vue"),
        name: "photo",
        redirect: "/main/photo/photoList",
        children: [
          {
            path: "photoList",
            component: () => import("./app/photo/photoList.vue"),
            name: "photoList"
          },
          {
            path: "photoAdd",
            component: () => import("./app/photo/photoAdd.vue"),
            name: "photoAdd"
          }
        ]
      },
      {
        path: "network",
        component: () => import("./app/index.vue"),
        name: "network",
        redirect: "/main/network/networkList",
        children: [
          {
            path: "networkList",
            component: () => import("./app/network/networkList.vue"),
            name: "networkList"
          },
          {
            path: "networkEdit",
            component: () => import("./app/network/networkEdit.vue"),
            name: "networkEdit"
          }
        ]
      },
      {
        path: "lab",
        component: () => import("./app/index.vue"),
        name: "lab",
        redirect: "/main/lab/lotto",
        children: [
          {
            path: "lotto",
            component: () => import("./app/lab/lotto/lotto.vue"),
            name: "lotto"
          },
          {
            path: "select",
            component: () => import("./app/index.vue"),
            name: "select",
            redirect: "/main/lab/select/step1",
            children: [
              {
                path: "step1",
                component: () => import("./app/lab/select/selectStep1.vue"),
                name: "select-step1"
              },
              {
                path: "step2",
                component: () => import("./app/lab/select/selectStep2.vue"),
                name: "select-step2"
              }
            ]
          },
          {
            path: "todo",
            component: () => import("./app/lab/todo/todo.vue"),
            name: "todo"
          }
        ]
      }
    ]
  },
  { path: "*", redirect: "/404" }
]

export default new Router({
  // mode: 'history',
  routes: constantRouterMap
})

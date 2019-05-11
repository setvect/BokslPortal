const boardManageRouter = {
  path: "boardManager",
  name: "board-manager",
  component: () => import("@/views/home/boardManager/index"),
  meta: { title: "게시판관리" },
  redirect: "/home/boardManager/list",
  hidden: false,
  children: [
    {
      path: "list",
      name: "board-manager-list",
      component: () => import("@/views/home/boardManager/boardManagerList"),
      hidden: true,
      meta: { title: "목록" }
    },
    {
      path: "add",
      name: "board-manager-add",
      component: () => import("@/views/home/boardManager/boardManagerAdd"),
      hidden: true,
      meta: { title: "등록" }
    }
  ]
}

export default boardManageRouter

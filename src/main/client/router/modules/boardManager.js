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
      META: { title: "목록" }
    },
    {
      path: "read",
      name: "board-manager-read",
      component: () => import("@/views/home/boardManager/boardManagerRead"),
      hidden: true,
      meta: { title: "읽기" }
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

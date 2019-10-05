export default {
  methods: {
    listPage() {
      this.$router.push({
        name: "boardManagerList"
      })
    },
    editPage(boardCode) {
      console.log('boardCode :', boardCode);
      this.$route.query.boardCode = boardCode;
      this.$router.push({
        name: "boardManagerAdd",
        query: this.$route.query
      });
    },
    deleteProc(boardCode) {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/board-manager/item/${boardCode}`, {}, (res) => {
          if (this.$route.name === "boardManagerList") {
            this.listProc();
          } else {
            this.listPage();
          }
        });
      });
    }
  }
}

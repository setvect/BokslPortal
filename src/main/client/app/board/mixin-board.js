export default {
  methods: {
    listPage() {
      delete this.$route.query.boardCode;
      this.$router.push({
        name: "boardList",
        query: this.$route.query
      });
    },
    editPage(boardCode) {
      this.$route.query.boardCode = boardCode;
      this.$router.push({
        name: "boardAdd",
        query: this.$route.query
      })
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
        VueUtil.delete(`/board-article/item/${boardCode}`, {}, (res) => {
          this.listPage();
        });
      });

    }
  }
}

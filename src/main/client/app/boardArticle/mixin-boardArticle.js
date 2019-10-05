export default {
  methods: {
    listPage() {
      delete this.$route.query.boardArticleSeq;
      this.$router.push({
        name: "boardArticleList",
        query: this.$route.query
      });
    },
    editPage(boardArticleSeq) {
      this.$route.query.boardArticleSeq = boardArticleSeq;
      this.$router.push({
        name: "boardArticleAdd",
        query: this.$route.query
      })
    },
    deleteProc(boardArticleSeq) {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/board-article/item/${boardArticleSeq}`, {}, (res) => {
          this.listPage();
        });
      });

    }
  }
}

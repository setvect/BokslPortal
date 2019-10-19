import store from "../../store/index.js";

export default {
  data() {
    return {
      boardManager: null
    };
  },
  computed: {
    isEncrypt() {
      if (this.boardManager == null) {
        return false;
      }
      return this.boardManager.encryptF;
    }
  },
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
    },
    // 게시판 정보
    loadBoardManager() {
      this.boardManager = store.state.board.boardManager;
      if (this.boardManager != null && this.boardManager.boardCode == this.$route.query.boardCode) {
        console.log("load cancel");
        return;
      }
      store.dispatch('board/loadBoardManager', this.$route.query.boardCode).then(() => {
        console.log("loadded");
        this.boardManager = store.state.board.boardManager;
      });
    }
  }
}

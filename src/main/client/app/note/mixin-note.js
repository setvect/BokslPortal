export default {
  methods: {
    listPage() {
      delete this.$route.query.noteSeq;
      this.$router.push({
        name: "noteList",
        query: this.$route.query
      });
    },
    editPage(noteSeq) {
      this.$route.query.noteSeq = noteSeq;
      this.$router.push({
        name: "noteAdd",
        query: this.$route.query
      });
    },
    deleteProc(noteSeq) {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/note/item/${noteSeq}`, {}, (res) => {
          if (this.$route.name === "noteList") {
            this.listProc();
          }else{
            this.listPage();
          }
        });
      });
    },
  }
}

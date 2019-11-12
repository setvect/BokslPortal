import store from "../../store/index.js";
export default {
  data() {
    return {
      categoryList: [],
    };
  },
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
          } else {
            this.listPage();
          }
        });
      });
    },
    loadCategory() {
      store.dispatch('note/loadTree').then(() => {
        let cList = $.extend(true, [], store.state.note.categoryTree.children);
        this.travelCategory(cList, 1);
      });
    },
    travelCategory(cList, level) {
      let space = '__'.repeat(level);
      cList.forEach((c) => {
        c.data.name = space + c.data.name;
        this.categoryList.push(c.data);
        this.travelCategory(c.children, level + 1);
      })
    }
  }
}

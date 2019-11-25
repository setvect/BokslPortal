import cookies from 'js-cookie';

export default {
  data() {
    return {
      searchData: {
        categorySeq: null,
        word: null,
      },
    }
  },
  methods: {
    deleteProc(memoSeq) {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/memo/item/${memoSeq}`, {}, (res) => {
          this.$route.query.filterWord = "__temp";
          this.$router.push({ name: "memoList", query: { filterWord: this.searchData.word } }).catch(err => { });
        });
      });
    },
  },
  mounted() {
    this.searchData.categorySeq = cookies.get("BokslMemoCategory");
  }
}

export default {
  methods: {
    listPage() {
      this.$router.push({ name: "memoList" })
    },
    editPage() {
      this.$router.push({ name: "memoAdd" })
    },
    deleteProc() {
      console.log("삭제")
    }
  }
}

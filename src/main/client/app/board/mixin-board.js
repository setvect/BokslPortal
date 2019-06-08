export default {
  methods: {
    listPage() {
      this.$router.push({ name: "boardList" })
    },
    editPage() {
      this.$router.push({ name: "boardAdd" })
    },
    deleteProc() {
      console.log("삭제")
    }
  }
}

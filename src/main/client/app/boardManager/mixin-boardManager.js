export default {
  methods: {
    listPage() {
      this.$router.push({ name: "boardManagerList" })
    },
    editPage() {
      this.$router.push({ name: "boardManagerAdd" })
    },
    deleteProc() {
      console.log("삭제")
    }
  }
}

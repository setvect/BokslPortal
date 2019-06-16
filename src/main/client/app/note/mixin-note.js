export default {
  methods: {
    listPage() {
      this.$router.push({ name: "noteList" })
    },
    editPage() {
      this.$router.push({ name: "noteAdd" })
    },
    deleteProc() {
      console.log("삭제")
    }
  }
}

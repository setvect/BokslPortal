export default {
  methods: {
    listPage() {
      this.$router.push({ name: "knowledgeList" })
    },
    editPage() {
      this.$router.push({ name: "knowledgeAdd" })
    },
    deleteProc() {
      console.log("삭제")
    }
  }
}

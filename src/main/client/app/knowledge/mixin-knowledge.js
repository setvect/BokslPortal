export default {
  data() {
    return {
      classifyList: [],
    };
  },
  methods: {
    listPage() {
      this.$router.push({
        name: "knowledgeList"
      })
    },
    editPage() {
      this.$router.push({
        name: "knowledgeAdd"
      })
    },
    deleteProc() {
      console.log("삭제")
    },
    loadCodeList() {
      VueUtil.get('/code/list/KNOW_TYPE', {}, (res) => {
        this.classifyList = res.data;
      });
    }
  },
  mounted() {
    this.loadCodeList();
  }
}

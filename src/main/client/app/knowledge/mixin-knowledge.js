export default {
  data() {
    return {
      classifyList: [],
    };
  },
  methods: {
    listPage() {
      this.$router.push({
        name: "knowledgeList",
        query: this.$route.query
      })
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

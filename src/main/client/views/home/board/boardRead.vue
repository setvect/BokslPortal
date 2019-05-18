<template>
  <div class="app-container">
    <el-row>
      <el-form ref="form" label-width="120px">
        <el-form-item label="제목">{{item.boardCode}}</el-form-item>
        <el-form-item label="본문"><div v-html="item.content"></div></el-form-item>
        <el-form-item>
          <el-button size="medium" type="primary" @click="editPage()">수정</el-button>
          <el-button size="medium" @click="listPage()">목록</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>
</template>

<script>
import "@/utils/vue-common"
export default {
  data() {
    return {
      item: {
        title: "제목입니다.",
        content: '내용',
      }
    }
  },
  methods: {
    editPage() {
      this.$router.push({ name: 'board-add' })
    },
    listPage() {
      this.$router.push({ name: 'board-list' })
    },
  }
}
</script>
<style scoped>
</style>


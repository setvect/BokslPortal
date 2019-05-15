<template>
  <div class="app-container">
    <el-row>
      <el-form ref="form" label-width="120px">
        <el-form-item label="코드">{{item.boardCode}}</el-form-item>
        <el-form-item label="이름">{{item.name}}</el-form-item>
        <el-form-item label="업로드 용량제한">{{item.uploadLimit}}</el-form-item>
        <el-form-item label="댓글 사용">{{item.commentF}}</el-form-item>
        <el-form-item label="파일 업로드">{{item.attachF}}</el-form-item>
        <el-form-item label="암호화 글 등록">{{item.encodeF}}</el-form-item>
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
        boardCode: "BDAABB00",
        name: '공지사항',
        uploadLimit: 1000,
        commentF: false,
        attachF: true,
        encodeF: false
      }
    }
  },
  methods: {
    editPage() {
      this.$router.push({ name: 'board-manager-add' })
    },
    listPage() {
      this.$router.push({ name: 'board-manager-list' })
    },
  }
}
</script>
<style>
  .row{
    background-color: red;
  }
</style>


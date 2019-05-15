<template>
  <div class="app-container">
    <el-row>
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="코드">
          <el-input v-model="form.boardCode" style="max-width:200px;"/>
        </el-form-item>
        <el-form-item label="이름">
          <el-input v-model="form.name" style="max-width:200px;"/>
        </el-form-item>

        <el-form-item label="업로드 용량제한">
          <el-input v-model.number="form.uploadLimit" type="number" style="max-width:200px;"/>(KB)
        </el-form-item>

        <el-form-item label="댓글 사용">
          <el-switch v-model="form.commentF"/>
        </el-form-item>
        <el-form-item label="파일 업로드">
          <el-switch v-model="form.attachF"/>
        </el-form-item>
        <el-form-item label="암호화 글 등록">
          <el-switch v-model="form.encodeF"/>
        </el-form-item>
        <el-form-item>
          <el-button size="medium" type="primary" @click="onSubmit()">확인</el-button>
          <el-button size="medium" @click="listPage()">취소</el-button>
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
      form: {
        boardCode: "BDAABB00",
        name: '',
        uploadLimit: 1000,
        commentF: false,
        attachF: true,
        encodeF: false
      }
    }
  },
  methods: {
    edit() {
      console.log("수정")
    },
    onSubmit() {
      this.$message('submit!')
    },
    listPage() {
      this.$router.push({ name: 'board-manager-list' })
    }
  }
}
</script>


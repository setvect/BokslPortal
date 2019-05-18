<template>
  <div class="app-container">
    <el-row>
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="제목">
          <el-input v-model="form.title"/>
        </el-form-item>

        <el-form-item label="내용">
          <el-input v-model="form.content" type="textarea"/>
        </el-form-item>

        <el-form-item label="파일">
          <el-upload
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="fileList"
            class="upload-demo"
            action="/"
            multiple
          >
            <el-button size="small" type="primary">파일 업로드</el-button>
            <div slot="tip" class="el-upload__tip">업로드 용량 제한: 20메가</div>
          </el-upload>
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
        title: "제목입다.",
        content: "내용..."
      },
      fileList: []
    }
  },
  methods: {
    onSubmit() {
      this.$message('submit!')
    },
    listPage() {
      this.$router.push({ name: 'board-list' })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`The limit is 3, you selected ${files.length} files this time, add up to ${files.length + fileList.length} totally`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`Cancel the transfert of ${file.name} ?`);
    }

  }
}
</script>
<style scoped>
</style>


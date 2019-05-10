<template>
  <div class="app-container">
    <el-row style="padding-buttom:20px;">
      <el-col>
        <el-select value="code" placeholder="Select" size="mini" style="width:100px;">
          <el-option label="코드" value="code"/>
          <el-option label="이름" value="name"/>
        </el-select>
        <el-input placeholder="검색어" size="mini" style="width:200px;"/>
        <el-button size="mini">검색</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-button size="medium">확인</el-button>
    </el-row>
  </div>
</template>

<script>
import "@/utils/vue-common"
export default {
  data() {
    return {
    }
  },
  methods: {
    edit() {
      console.log("수정")
    },
  }
}
</script>


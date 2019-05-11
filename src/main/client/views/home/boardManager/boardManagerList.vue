<template>
  <div class="app-container">
    <el-row style="padding-buttom:20px;">
      <el-col>
        <el-select v-model="type" placeholder="Select" size="mini" style="width:100px;">
          <el-option label="코드" value="code"/>
          <el-option label="이름" value="name"/>
        </el-select>
        <el-input placeholder="검색어" size="mini" style="width:200px;"/>
        <el-button size="mini">검색</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-table :data="tableData" header-row-class-name="table-hd" border style="width: 100%">
        <el-table-column label="No." width="80" align="center">
          <template slot-scope="scope">{{ scope.$index | indexSeq({currentPage: 1, totalCount: 10, returnCount: 10}) }}</template>
        </el-table-column>
        <el-table-column prop="boardCode" label="코드"/>
        <el-table-column prop="codeValue" label="이름"/>
        <el-table-column label="수정" width="100">
          <template slot-scope="scope">
            <el-button size="mini" @click="edit()">수정</el-button>
          </template>
        </el-table-column>
        <el-table-column label="삭제" width="100">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="remove()">삭제</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
      <el-pagination :total="1000" background align="center" layout="prev, pager, next" @current-change="changePage"/>
    </el-row>
    <el-row>
      <el-button size="medium" @click="addForm()">만들기</el-button>
    </el-row>
  </div>
</template>

<script>
import "@/utils/vue-common"
export default {
  data() {
    return {
      tableData: [{
        boardCode: 'MainCode',
        minorCode: 'SubCode1',
        codeValue: '값1',
        orderNo: 10,
      }, {
        boardCode: 'MainCode',
        minorCode: 'SubCode2',
        codeValue: '값2',
        orderNo: 10,
      }, {
        boardCode: 'MainCode',
        minorCode: 'SubCode3',
        codeValue: '값3',
        orderNo: 10,
      }, {
        boardCode: 'MainCode',
        minorCode: 'SubCode4',
        codeValue: '값4',
        orderNo: 10,
      }],
      dialogFormVisible: false,
      form: {
        boardCode: 'ROOT',
        minorCode: '',
        codeValue: '',
        orderNo: 10,
      },
      formLabelWidth: '120px',
      type: 'code',
    }
  },
  methods: {
    edit() {
      console.log("수정")
    },
    remove() {
      console.log("삭제")
    },
    changePage(page) {
      console.log("page", page);
    },
    addForm() {
      console.log("#######################");
      console.log('this.$router :', this.$router);
      this.$router.push({ name: 'board-manager-add' })
    }
  }
}
</script>
<style scoped>
.el-row{
  margin-bottom: 10px;
}
</style>


<template>
  <div class="app-container">
    <el-row style="padding-buttom:20px;">
      <el-col :span="24">
        <span>
          현재코드:
          <code>aaa</code>
        </span>
        <el-button size="mini">상위코드 이동</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-table :data="tableData" header-row-class-name="table-hd" border style="width: 100%">
        <el-table-column label="No." width="80" align="center">
          <template slot-scope="scope">{{ scope.$index | indexSeq({currentPage: 1, totalCount: 10, returnCount: 10}) }}</template>
        </el-table-column>
        <el-table-column prop="majorCode" label="상위분류"/>
        <el-table-column prop="minorCode" label="코드"/>
        <el-table-column prop="codeValue" label="코드값"/>
        <el-table-column label="정렬" width="150">
          <template slot-scope="scope">
            <el-input v-model.number="scope.row.orderNo" size="mini" placeholder="정렬 번호" type="number"/>
          </template>
        </el-table-column>
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
      <el-button size="medium" @click="dialogFormVisible = true">코드 만들기</el-button>
      <el-button size="medium">정렬값 수정</el-button>
    </el-row>

    <el-dialog :visible.sync="dialogFormVisible" title="코드 등록">
      <el-form :model="form">
        <el-form-item label="상위분류" label-width="100px">
          <el-input v-model="form.majorCode" disabled/>
        </el-form-item>
        <el-form-item label="하위분류" label-width="100px">
          <el-input v-model="form.minorCode"/>
        </el-form-item>
        <el-form-item label="코드값" label-width="100px">
          <el-input v-model="form.codeValue"/>
        </el-form-item>
        <el-form-item label="수정" label-width="100px">
          <el-input v-model.number="form.orderNo" type="number"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">닫기</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">확인</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import "@/utils/vue-common"
export default {
  data() {
    return {
      tableData: [{
        majorCode: 'MainCode',
        minorCode: 'SubCode1',
        codeValue: '값1',
        orderNo: 10,
      }, {
        majorCode: 'MainCode',
        minorCode: 'SubCode2',
        codeValue: '값2',
        orderNo: 10,
      }, {
        majorCode: 'MainCode',
        minorCode: 'SubCode3',
        codeValue: '값3',
        orderNo: 10,
      }, {
        majorCode: 'MainCode',
        minorCode: 'SubCode4',
        codeValue: '값4',
        orderNo: 10,
      }],
      dialogFormVisible: false,
      form: {
        majorCode: 'ROOT',
        minorCode: '',
        codeValue: '',
        orderNo: 10,
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    edit() {
      console.log("수정")
    },
    remove() {
      console.log("삭제")
    }
  }
}
</script>
<style scoped>
.el-row{
  margin-bottom: 10px;
}
</style>


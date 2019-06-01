<template>
  <div>
    <b-table :bordered="true" hover :fields="fields" :items="codeList">
      <template slot="index" slot-scope="data">{{ data.index + 1 }}</template>
      <template slot="orderNo" slot-scope="data">
        <b-form-input id="input-1" v-model.number="data.item.orderNo " type="number" required/>
      </template>
    </b-table>
    <b-button @click="addPage()" type="button" variant="info">코드 만들기</b-button>
    <b-button type="button" variant="info">정렬값 수정</b-button>
    <add ref="add"/>
  </div>
</template>

<script>
import add from "./code-add.vue"

export default {
  components: {
    add,
  },
  data() {
    return {
      fields: [
        { key: 'index', label: "#" },
        { key: 'majorCode', label: "대분류" },
        { key: 'minorCode', label: "소분류" },
        { key: 'codeValue', label: "코드값" },
        { key: 'orderNo', label: "순서", class: 'col-order' },
      ],
      codeList: [{
        majorCode: 'MainCode',
        minorCode: 'SubCode1',
        codeValue: '값1',
        orderNo: 10,
      }, {
        majorCode: 'MainCode',
        minorCode: 'SubCode2',
        codeValue: '값2',
        orderNo: 11,
      }, {
        majorCode: 'MainCode',
        minorCode: 'SubCode3',
        codeValue: '값3',
        orderNo: 12,
      }, {
        majorCode: 'MainCode',
        minorCode: 'SubCode4',
        codeValue: '값4',
        orderNo: 13,
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
    },
    addPage() {
      console.log('this.$refs :', this.$refs);
      console.log('this.$refs.add :', this.$refs.add);
      this.$refs.add.open();
    }
  }
}
</script>

<style lang="css" >
  .col-order{
    width: 200px;
  }
</style>
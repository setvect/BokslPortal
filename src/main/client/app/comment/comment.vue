<template>
  <div>
    <div style="margin-bottom:20px;">
      <b-row>
        <b-col sm="10">
          <b-textarea v-model="content" v-validate="{required: true}" :state="validateState('content')" name="content" placeholder="써라" data-vv-as="내용 "></b-textarea>
          <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
        </b-col>
        <b-col sm="2">
          <b-button @click="addProc()" type="button" variant="primary">확인</b-button>
        </b-col>

      </b-row>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="page.list" thead-class="hidden_header">
      <template slot="index" slot-scope="data">{{ data.index | indexSeq(page) }}</template>
      <template slot="content" slot-scope="data">
        <span v-br="data.item.content"></span>
      </template>
      <template slot="regDate" slot-scope="data">{{data.item.regDate | dateFormat('YYYY-MM-DD')}}</template>
      <template slot="function" slot-scope="data">
        <b-link @click="deleteProc(data.item.boardArticleSeq)">삭제</b-link>
      </template>
    </b-table>
  </div>
</template>

<script>
export default {
  mixins: [comFunction],
  data() {
    return {
      content: "",
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "content", label: "글" },
        { key: "regDate", label: "날짜", class: 'date-col' },
        { key: "function", label: "기능", class: 'function-col' }
      ],
      page: {
        totalCount: 100,
        startCursor: 0,
        list: [
          {
            content: "복슬이\n바보1",
            regDate: 1561071320000
          },
          {
            content: "복슬이\n바보2",
            regDate: 1561071320000
          },
          {
            content: "복슬이\n바보3",
            regDate: 1561071320000
          },
        ]
      }
    };
  },
  props: {
    moduleName: {
      type: String
    },
    moduleId: {
      type: String
    },
  },
  methods: {
    search() {
      this.page.list = [];
      this.page.totalCount = -1;
    },
    addProc(event) {
      this.$validator.validate().then((result) => {
        console.log('result :', result);
        if (!result) {
          return;
        }
        console.log("add");
      });
    },

  },
  mounted() {
  }
};
</script>

<style>
  .index-col{
    width: 50px;
  }
  .function-col{
    width: 140px;
  }
  .date-col {
    width: 140px;
  }
  .hidden_header {
    display: none;
  }
</style>

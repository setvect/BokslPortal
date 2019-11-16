<template>
  <div>
    <div style="margin-bottom:20px;">
      <b-row>
        <b-col sm="10">
          <b-textarea v-model="content" v-validate="{required: true}" :state="validateState('content')" class="_content" name="content" placeholder="써라" data-vv-as="내용 "></b-textarea>
          <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
        </b-col>
        <b-col sm="2">
          <b-button @click="addProc()" type="button" variant="primary">확인</b-button>
        </b-col>
      </b-row>
    </div>

    <table role="table" aria-busy="false" aria-colcount="4" class="table b-table table-hover table-bordered" id="__BVID__37">
      <thead role="rowgroup" class="hidden_header">
        <tr role="row" class>
          <th role="columnheader" scope="col" aria-colindex="2" class>글</th>
          <th role="columnheader" scope="col" aria-colindex="4" class="function-col">기능</th>
        </tr>
      </thead>
      <tbody role="rowgroup">
        <tr v-for="(item) in page.list" :key="item.commentSeq" role="row">
          <td role="cell" aria-colindex="2" class>
            <span v-br="item.content"></span>
            <span style="float:right">{{item.regDate | relativeDate}}</span>
          </td>
          <td role="cell" aria-colindex="4" class="function-col">
            <b-link @click="deleteProc(item)"><i class="fa fa-trash"></i></b-link>
          </td>
        </tr>
      </tbody>
    </table>

    <div style="padding-bottom: 20px;">
      <b-button @click="nextProc()" block variant="outline-secondary" size="sm">더보기({{page.list.length | numberFormat}}/{{page.totalCount | numberFormat}})</b-button>
    </div>
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
        list: [],
        totalCount: -1,
        startCursor: 0,
      },
      startCursor: 0,
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
    nextProc() {
      this.startCursor = this.page.list.length;
      if (this.startCursor == this.page.totalCount) {
        this.$notify({
          group: 'message-noti',
          type: 'warn',
          text: '더 이상 없다.'
        });
      };
      let param = {
        startCursor: this.startCursor,
        moduleName: this.moduleName,
        moduleId: this.moduleId
      };
      VueUtil.get("/comment/page", param, (res) => {
        this.page.totalCount = res.data.totalCount;
        this.page.list = this.page.list.concat(res.data.list);
      });
    },
    addProc(event) {
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }
        VueUtil.post("/comment/item", {
          content: this.content,
          moduleName: this.moduleName,
          moduleId: this.moduleId,
        }, (res) => {
          this.page.list.unshift(res.data);
          this.page.totalCount++;
          // this.content = "";
        });
      });
    },
    deleteProc(item) {
      Swal.fire({
        title: '삭제할거야?',
        type: 'info',
        showCloseButton: true,
        showCancelButton: true,
      }).then((result) => {
        if (!result.value) {
          return;
        }
        VueUtil.delete(`/comment/item/${item.commentSeq}`, {}, (res) => {
          this.page.list = this.page.list.filter(i => i !== item);
          this.page.totalCount--;
        });
      });
    },
  },
  mounted() {
    this.nextProc();
  }
};
</script>

<style>
  .function-col{
    text-align: center;
    width: 35px;
  }
  .hidden_header {
    display: none;
  }
</style>

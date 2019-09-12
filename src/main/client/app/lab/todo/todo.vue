<template>
  <div>
    <div>
      <b-form @submit.stop.prevent inline style="margin: 59px 0 10px; 0 ">
        <!-- <input type="text" class="form-control daterange _datepicker" readonly="readonly" /> -->
        <b-form-group>
          <b-form-checkbox-group v-model="searchParam.statusType" :options="options" switches></b-form-checkbox-group>
        </b-form-group>
        <b-input @keypress.13="nextProc()" v-model="searchParam.word" style="margin-right:5px;" size="sm" placeholder="검색어"></b-input>
        <b-button @click="search()" variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addForm()" size="sm" type="button" variant="info">만들기</b-button>
      </b-form>
    </div>
    <b-row style="margin:0px;">
      <b-card
        v-for="item in page.list"
        :key="item.todoSeq"
        :title="item.content"
        :bg-variant="getStyle(item).bg"
        :text-variant="getStyle(item).text"
        footer-bg-variant="white"
        tag="article"
        style="max-width: 20rem; margin-right:10px;min-width:250px;"
        class="mb-2"
      >
        <b-card-text>
          {{item.editDate | dateFormat('YYYY-MM-DD') }}
          <span>{{item.checkType}}</span>
          <span v-show="item.checkDate != null" style="padding-left:20px; float:right">{{item.checkDate | dateFormat('YYYY-MM-DD')}}</span>
        </b-card-text>
        <div slot="footer">
          <b-button href="#" variant="outline-danger" size="sm" @click="deleteProc(item)">삭제</b-button>
          <b-button v-show="item.statusType === 'PLAN'" href="#" variant="outline-primary" size="sm" @click="editForm(item)">수정</b-button>
          <b-button v-show="item.statusType === 'PLAN'" href="#" variant="outline-warning" size="sm" @click="giveUpProc(item)">포기</b-button>
          <b-button v-show="item.statusType === 'PLAN'" href="#" variant="outline-success" size="sm" @click="completeProc(item)">완료</b-button>
          <b-button v-show="item.statusType !== 'PLAN'" href="#" variant="outline-success" size="sm" @click="cancelProc(item)">취소</b-button>
        </div>
      </b-card>
    </b-row>
    <div>
      <b-button @click="nextProc()" block variant="outline-secondary" size="sm">더보기({{page.list.length | numberFormat}}/{{page.totalCount | numberFormat}})</b-button>
    </div>

    <b-modal ref="todoForm" title="할일 만들기" @ok.prevent="confirmEvent" @shown="shownAddEvent">
      <div>
        <b-form autocomplete="off" @submit.stop.prevent>
          <b-form-group label="내용" label-for="input-content">
            <b-form-input
              ref="content-input"
              @keypress.13="confirmEvent"
              v-model="item.content"
              name="content"
              v-validate="{ required: true}"
              state="validateState('content')"
              id="input-content"
              data-vv-as="내용 "
            ></b-form-input>
            <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>
    <notifications group="message-noti" position="top center" />
  </div>
</template>

<script>
import '../../../utils/vue-common.js'
import _ from "lodash";

export default {
  mixins: [comFunction],
  data() {
    return {
      options: [
        { text: '예정', value: 'PLAN' },
        { text: '완료', value: 'COMPLETE' },
        { text: '포기', value: 'GIVEUP' }
      ],
      periodOption: [
        { text: '한번만', value: 'ONCE' },
        { text: '매일', value: 'DAY' },
        { text: '주', value: 'WEEK' },
        { text: '월', value: 'MONTH' },
      ],
      item: {
        content: '',
      },
      // 쓰기 상태, 수정 상태 여부
      confirmEvent: {},
      page: {
        list: [],
        totalCount: -1,
      },
      searchParam: {
        word: null,
        statusType: ['PLAN'],
        startCursor: 0,
      },
    };
  },
  methods: {
    nextProc() {
      this.searchParam.startCursor = this.page.list.length;
      if (this.searchParam.startCursor == this.page.totalCount) {
        this.$notify({
          group: 'message-noti',
          type: 'warn',
          text: '더 이상 없다.'
        });
      }
      VueUtil.get("/todo/page", this.searchParam, (res) => {
        this.page.totalCount = res.data.totalCount;
        this.page.list = this.page.list.concat(res.data.list);
      });
    },
    search() {
      this.page.list = [];
      this.page.totalCount = -1;
      this.nextProc();
    },
    addForm() {
      this.item = { content: "" };
      this.$refs['todoForm'].show();
      this.confirmEvent = this.addProc;
    },
    editForm(item) {
      this.item = $.extend(true, {}, item);;
      this.$refs['todoForm'].show()
      this.confirmEvent = this.editProc;
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
        VueUtil.delete(`/todo/item/${item.todoSeq}`, {}, (res) => {
          this.page.list = this.page.list.filter(i => i !== item);
        });
      });;
    },
    giveUpProc(item) {
      this.changeStatusProc(item.todoSeq, 'GIVEUP');
    },
    completeProc(item) {
      this.changeStatusProc(item.todoSeq, 'COMPLETE');
    },
    cancelProc(item) {
      this.changeStatusProc(item.todoSeq, 'PLAN');
    },
    changeStatusProc(todoSeq, statusType) {
      VueUtil.patch('/todo/check', { todoSeq: todoSeq, statusType: statusType }, (res) => {
        let idx = _.findIndex(this.page.list, (ele) => ele.todoSeq === todoSeq);
        // array element 변경사항을 vue가 알 수 있도록
        this.page.list.splice(idx, 1, res.data);
      });
    },
    addProc(event) {
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }
        VueUtil.post("/todo/item", this.item, (res) => {
          this.$refs['todoForm'].hide();
          this.page.list.unshift(res.data);
        });
      });
    },
    editProc(event) {
      this.$validator.validateAll().then((result) => {
        if (!result) {
          return;
        }
        VueUtil.put("/todo/item", this.item, (res) => {
          let idx = _.findIndex(this.page.list, (ele) => ele.todoSeq === this.item.todoSeq);
          this.page.list.splice(idx, 1, res.data);
          this.$refs['todoForm'].hide();
        });
      });
    },
    // 입력 창 오픈시
    shownAddEvent(event) {
      this.$refs['content-input'].focus();
    },
    getStyle(item) {
      if (item.statusType === 'COMPLETE') {
        return { bg: "success", text: 'white' };
      }
      if (item.statusType === 'GIVEUP') {
        return { bg: "warning", text: 'white' };
      }
      return { bg: "", text: '' };
    },
  },
  mounted() {
    console.log('comFunction :', comFunction);
    this.nextProc();
  }
};
</script>

<style >
  .index-col{
    width: 50px;
  }
  .content-col{
     max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .date-col{
    width: 140px;
  }
</style>

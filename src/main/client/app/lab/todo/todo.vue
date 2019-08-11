<template>
  <div>
    <div>
      <b-form inline style="margin: 59px 0 10px; 0 ">
        <!-- <input type="text" class="form-control daterange _datepicker" readonly="readonly" /> -->
        <b-form-group style="padding-right:20px;">
          <datepicker v-model="searchData.baseDate" :language="ko" format="yyyy-MM-dd" input-class="form-control form-control-sm" placeholder="기준일"></datepicker>
        </b-form-group>
        <b-form-group>
          <b-form-checkbox-group v-model="searchData.status" :options="options" switches></b-form-checkbox-group>
        </b-form-group>
        <b-input v-model="searchData.word" style="margin-right:5px;" size="sm" placeholder="검색어"></b-input>
        <b-button @click="listProc()" variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
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
        style="max-width: 20rem; margin-right:10px"
        :style="{'opacity':item.enable ? 1 : 0.35}"
        class="mb-2"
      >
        <b-card-text>
          {{item.editDate | dateFormat('YYYY-MM-DD') }}
          <span>({{getPeriodLabel(item.period)}})</span>
          <span v-show="item.completeDate != null" style="float:right">{{item.completeDate | dateFormat('YYYY-MM-DD')}}</span>
        </b-card-text>
        <div slot="footer">
          <b-button href="#" variant="outline-danger" size="sm" @click="deleteProc(item)">삭제</b-button>
          <b-button v-show="item.status === 'plan'" href="#" variant="outline-primary" size="sm" @click="editForm(item)">수정</b-button>
          <b-button v-show="item.status === 'plan'" href="#" variant="outline-warning" size="sm" @click="givenUpProc(item)">포기</b-button>
          <b-button v-show="item.status === 'plan'" href="#" variant="outline-success" size="sm" @click="completeProc(item)">완료</b-button>
          <b-button v-show="item.status !== 'plan'" href="#" variant="outline-success" size="sm" @click="cancelProc(item)">취소</b-button>
        </div>
      </b-card>
    </b-row>
    <div>
      <b-button block variant="outline-secondary" size="sm">더보기(100/200)</b-button>
    </div>

    <b-modal ref="todoForm" title="할일 만들기" @ok="addProc" @shown="shownAddEvent">
      <div>
        <b-form autocomplete="off">
          <b-form-group label="내용" label-for="input-content">
            <b-form-input ref="content-input" v-model="item.content" name="content" v-validate="{ required: true}" :state="validateState('content')" id="input-content" data-vv-as="내용 "></b-form-input>
            <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
          </b-form-group>
          <b-form-group label="주기" label-for="period-slots">
            <b-form-radio-group id="period-slots" v-model="item.period" :options="periodOption" name="period-options-slots"></b-form-radio-group>
          </b-form-group>
          <b-form-group v-show="item.period != 'ONCE'" label="수행기간" label-for="input-duration">
            <input type="text" id="input-duration" class="form-control daterange _datepicker" readonly="readonly" />
          </b-form-group>
        </b-form>
      </div>
    </b-modal>
  </div>
</template>

<script>
import comFunction from "../../commonFunction.js";
import '../../../utils/vue-common.js'
import VueUtil from '../../../utils/vue-util.js'
import "daterangepicker";
import 'daterangepicker/daterangepicker.css';
import { ko } from 'vuejs-datepicker/dist/locale'
import Datepicker from 'vuejs-datepicker';
import moment from "moment";

export default {
  mixins: [comFunction],
  components: {
    Datepicker
  },
  data() {
    return {
      options: [
        { text: '예정', value: 'plan' },
        { text: '완료', value: 'complete' },
        { text: '포기', value: 'giveup' }
      ],
      periodOption: [
        { text: '한번만', value: 'ONCE' },
        { text: '매일', value: 'DAY' },
        { text: '주', value: 'WEEK' },
        { text: '월', value: 'MONTH' },
      ],
      item: {
        period: 'ONCE',
        content: '',
        durationFrom: 1561071320000,
        durationTo: 1561071320000,
      },
      page: {},
      searchData: {
        word: null,
        status: ['plan', 'complete'],
        baseDate: (new Date()).getTime()
      },
      ko: ko,
    };
  },
  methods: {
    listProc() {
      VueUtil.get("/todo/list", this.searchData, (res) => {
        this.page = res.data;
      });
    },
    addForm() {
      this.$refs['todoForm'].show()
    },
    editForm(item) {
      console.log('item 수정 :', item);
    },
    deleteProc(item) {
      VueUtil.delete(`/todo/item/${item.todoSeq}`, {}, (res) => {
        this.page.list = this.page.list.filter(i => i !== item);
      });

    },
    givenUpProc(item) {
      console.log('item 포기 :', item);
    },
    completeProc(item) {
      console.log('item 완료 :', item);
    },
    addProc(event) {
      event.preventDefault();
      this.$validator.validate().then((result) => {
        if (!result) {
          return false;
        }
        VueUtil.post("/todo/item", this.item, (res) => {
          this.$refs.todoForm.hide();
          this.listProc();
        });
      });
    },
    getPeriodLabel(period) {
      return this.periodOption.find((item) => item.value === period).text;
    },
    cancelProc(item) {
      console.log('취소');
    },
    // 입력 창 오픈시
    shownAddEvent(event) {
      $("._datepicker").daterangepicker({
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        },
        startDate: moment(this.item.durationFrom),
        endDate: moment(this.item.durationTo)
      }, (from, to) => {
        console.log('from :', from.valueOf());
        console.log('to :', to);
        this.item.durationFrom = from.valueOf();
        this.item.durationTo = to.valueOf();
      });
      this.$refs['content-input'].focus();
    },
    getStyle(item) {
      if (item.status === 'complete') {
        return { bg: "success", text: 'white' };
      }
      if (item.status === 'giveup') {
        return { bg: "warning", text: 'white' };
      }
      return { bg: "", text: '' };
    }
  },
  mounted() {
    this.listProc();
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

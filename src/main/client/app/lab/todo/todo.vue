<template>
  <div>
    <div>
      <b-form inline style="margin: 59px 0 10px; 0 ">
        <b-form-group>
          <b-form-checkbox-group v-model="selected" :options="options" switches></b-form-checkbox-group>
        </b-form-group>
        <b-form-checkbox class="mb-2 mr-sm-2 mb-sm-0" :checked="true">비활성화 포함</b-form-checkbox>
        <b-input v-model="searchData.word" style="margin-right:5px;" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addForm()" size="sm" type="button" variant="info">만들기</b-button>
      </b-form>
    </div>
    <b-row style="margin:0px;">
      <b-card
        v-for="item in listData"
        :key="item.networkSeq"
        :title="item.title"
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
          <b-button href="#" variant="outline-primary" size="sm" @click="editForm(item)">수정</b-button>
          <b-button href="#" variant="outline-warning" size="sm" @click="givenUpProc(item)">포기</b-button>
          <b-button href="#" variant="outline-success" size="sm" @click="completeProc(item)">완료</b-button>
        </div>
      </b-card>
    </b-row>
    <div>
      <b-button block variant="outline-secondary" size="sm">더보기(100/200)</b-button>
    </div>

    <b-modal ref="todoForm" title="할일 만들기" no-fade @ok="addProc()" @show="showAddEvent()">
      <div>
        <b-form>
          <b-form-group label="내용" label-for="input-content">
            <b-form-input id="input-content"></b-form-input>
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
import '../../../utils/vue-common.js'
import "daterangepicker";
import 'daterangepicker/daterangepicker.css';
import moment from "moment";

export default {
  data() {
    return {
      selected: ['plan', 'complete'], // Must be an array reference!
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
        durationForm: 1561071320000,
        durationTo: 1561071320000,
      },
      listData: [
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          period: 'ONCE',
          enable: true,
          regDate: 1561071320000,
          completeDate: 1582075320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'giveup',
          period: 'ONCE',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'plan',
          period: 'DAY',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'giveup',
          period: 'WEEK',
          enable: false,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          period: 'MONTH',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          period: 'ONCE',
          enable: true,
          regDate: 1561071320000
        },
      ],
      searchData: {
        categorySeq: 2,
        word: null,
      },
    };
  },
  methods: {
    addForm() {
      this.$refs['todoForm'].show()
    },
    editForm(item) {
      console.log('item 수정 :', item);
    },
    deleteProc(item) {
      console.log('item 삭제 :', item);
    },
    givenUpProc(item) {
      console.log('item 포기 :', item);
    },
    completeProc(item) {
      console.log('item 완료 :', item);
    },
    addProc() {
      console.log("addProc");
    },
    getPeriodLabel(period){
      return this.periodOption.find((item)=> item.value === period).text;
    },
    showAddEvent() {
      console.log("showAddEvent...");
      this.$nextTick(() => {
        console.log('$("._datepicker") :', $("._datepicker"));

      });
      setTimeout(() => {
        $("._datepicker").daterangepicker({
          showDropdowns: true,
          locale: {
            format: 'YYYY-MM-DD'
          },
          startDate: moment(this.item.durationForm),
          endDate: moment(this.item.durationTo)
        }, (start) => {
          console.log('start.format("YYYY-MM-DD") :', start.format("YYYY-MM-DD"));
        });
      }, 100); // TODO 이렇게 하지말기. 현재로썬 이렇게 안하면 daterangepicker가 적용되지 않음.
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

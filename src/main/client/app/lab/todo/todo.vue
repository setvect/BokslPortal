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

    <b-modal ref="todoForm" title="할일 만들기" @ok="addProc()">
      <div>
        <b-form>
          <b-form-group label="내용" label-for="input-label">
            <b-form-input id="input-label"></b-form-input>
          </b-form-group>
        </b-form>
      </div>
    </b-modal>
  </div>
</template>

<script>
import '../../../utils/vue-common.js'

export default {
  data() {
    return {
      selected: ['plan', 'complete'], // Must be an array reference!
      options: [
        { text: '예정', value: 'plan' },
        { text: '완료', value: 'complete' },
        { text: '포기', value: 'giveup' }
      ],
      listData: [
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          enable: true,
          regDate: 1561071320000,
          completeDate: 1582075320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'giveup',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'plan',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'giveup',
          enable: false,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
          enable: true,
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          status: 'complete',
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
    getStyle(item) {
      if (item.status == 'complete') {
        return { bg: "success", text: 'white' };
      }
      if (item.status == 'giveup') {
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

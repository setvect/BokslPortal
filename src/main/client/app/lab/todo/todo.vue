<template>
  <div>
    <div>
      <b-form inline style="margin: 59px 0 10px; 0 ">
        <b-form-checkbox class="mb-2 mr-sm-2 mb-sm-0" :checked="true">예정</b-form-checkbox>
        <b-form-checkbox class="mb-2 mr-sm-2 mb-sm-0" :checked="true">완료</b-form-checkbox>
        <b-form-checkbox class="mb-2 mr-sm-2 mb-sm-0" :checked="true">실패</b-form-checkbox>

        <b-input v-model="searchData.word" style="margin-right:5px;" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addForm()" size="sm" type="button" variant="info">만들기</b-button>
      </b-form>
    </div>
    <b-row style="margin:0px;">
      <b-card v-for="item in listData" :key="item.networkSeq" :title="item.title" tag="article" style="max-width: 20rem; margin-right:10px;" class="mb-2">
        <b-card-text>{{item.editDate | dateFormat('YYYY-MM-DD') }}</b-card-text>
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
      listData: [
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '시장 가서 장보기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
          regDate: 1561071320000
        },
        {
          todoSeq: 1,
          title: '책읽기',
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

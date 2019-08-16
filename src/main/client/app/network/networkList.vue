<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm" style="margin-right:30px;">검색</b-button>
        <b-button @click="addPage()" size="sm" type="button" variant="info">만들기</b-button>
      </b-form>
    </div>
    <b-row style="margin:0px;">
      <b-card v-for="item in page.list" :key="item.networkSeq" :title="item.title" tag="article" style="max-width: 20rem; margin-right:10px;cursor: pointer" class="mb-2" @click="readPage(item.networkSeq)">
        <b-card-text>수정일: {{item.editDate | dateFormat('YYYY-MM-DD') }}</b-card-text>
      </b-card>
    </b-row>
    <div>
      <b-button @click="nextProc()" block variant="outline-secondary" size="sm">더보기({{page.list.length | numberFormat}}/{{page.totalCount | numberFormat}})</b-button>
    </div>
    <notifications group="message-noti" position="top center" />
  </div>
</template>

<script>
import '../../utils/vue-common.js';
import comFunction from '../commonFunction.js';
import VueUtil from '../../utils/vue-util.js'

export default {
  mixins: [comFunction],
  data() {
    return {
      searchData: {
        word: null,
        statusType: ['PLAN'],
        startCursor: 0,
      },
      page: {
        list: [],
        totalCount: -1,
      },
      searchData: {
        categorySeq: 2,
        word: null,
      },
      page: {
        list: [],
        totalCount: -1,
      },
    };
  },
  methods: {
    nextProc() {
      this.searchData.startCursor = this.page.list.length;
      if (this.searchData.startCursor == this.page.totalCount) {
        this.$notify({
          group: 'message-noti',
          type: 'warn',
          text: '더 이상 없다.'
        });
      }
      VueUtil.get("/network/list", this.searchData, (res) => {
        this.page.totalCount = res.data.totalCount;
        this.page.list = this.page.list.concat(res.data.list);
      });
    },
    addPage() {
      this.$router.push({ name: "networkEdit" });
    },
    readPage(seq) {
      this.$router.push({ name: "networkEdit", query: { networkSeq: seq } });
    },

  },
  mounted() {
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

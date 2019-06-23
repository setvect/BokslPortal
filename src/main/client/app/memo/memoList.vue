<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.categorySeq" size="sm" style="width: 150px;">
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input>
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-table :bordered="true" hover :fields="fields" :items="listData">
      <template slot="index" slot-scope="data">{{ data.index + 1 }}</template>
      <template slot="content" slot-scope="data"><b-link @click="addPage(data.item.memoSeq)">{{ data.item.content }}</b-link></template>
      <template slot="editDate" slot-scope="data">{{data.item.editDate | dateFormat('YYYY-MM-DD')}}</template>
    </b-table>
    <b-row>
      <b-col style="text-align:right">
        <b-button @click="addPage()" type="button" variant="info">만들기</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import '../../utils/vue-common.js'

export default {
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "content", label: "내용", class: 'content-col' },
        { key: "editDate", label: "수정일", class: 'date-col' }
      ],
      listData: [
        {
          memoSeq: 1,
          content: "마지막 민방위 교육 예비군도 끝나고 민방위도 4년차. \n오늘은 내 마지막 민방위 교육이다. 광명시는 민방위 교육이 다른 곳 보다 풍성하다. 별도 자체 제작한 만화로 된 교재도 있고 빵과 음료수를 비롯한 간식도 준다. 의자도 편하다. 열심히 필기하면서 듣으라고 볼펜도 준다. 또 교육전 간단한 음악 공연도 있다. 작년엔 지방 파견 때문에 대전에서 교육을 받았다. 그 때와 비교하면 우리 광명시는 남한과 북한의 경제력을 보는 것 같다. 시청앞에 '채무 제로 선언. 시민이 주신 세금 소중이 쓰겠습니다.' 현수막이 걸려있던게 몸으로 .교육전 공연에 대해 말해 보겠다. ",
          editDate: 1561071320000
        },
        {
          memoSeq: 1,
          content: "복슬이",
          editDate: 1560178320000
        },
      ],
      searchData: {
        categorySeq: 2,
        word: null,
      },
      page: {
        total: 300,
        perPage: 10
      },
      categoryList: [
        {
          categorySeq: 1,
          name: "생활"
        }, {
          categorySeq: 2,
          name: "생각"
        }, {
          categorySeq: 3,
          name: "일"
        },
      ]
    };
  },
  methods: {
    addPage() {
      this.$router.push({ name: "memoAdd" });
    },
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

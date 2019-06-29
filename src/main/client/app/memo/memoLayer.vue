<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <b-form-select v-model="searchData.categorySeq" size="sm" style="width: 150px;">
          <option v-for="category in categoryList" :key="category.categorySeq" :value="category.categorySeq">{{category.name}}</option>
        </b-form-select>
        <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어" style="margin-right:30px;"></b-input>
        <b-button variant="primary" size="sm">등록</b-button>
        <b-button variant="secondary" size="sm">삭제취소</b-button>
      </b-form>
    </div>
    <div style="background: #ddd">
      aaaaaaaa
      <div class="_item" style="left: 266px; top: 49px; z-index: 2675; width: 150px; height: 150px; background:yellow">
        <span class="toolbar _header">
          16.08.28
          <b-button variant="info" size="sm" class="xs-btn">D</b-button>
          <b-button variant="info" size="sm" class="xs-btn">E</b-button>
          <b-button variant="info" size="sm" class="xs-btn">Done</b-button>
          <b-button variant="info" size="sm" class="xs-btn">S</b-button>
          <b-button variant="info" size="sm" class="xs-btn">W</b-button>
        </span>
        <div class="itemContent _content">여행의 마지막. 뭔갈 하날 끝내고 나면 인생의 마지막에 한 발짝 더 가까이 온 기분.</div>
      </div>
    </div>
  </div>
</template>

<script>
import '../../utils/vue-common.js'
import 'jquery-ui/themes/base/all.css';
import 'jquery-ui/ui/core.js';
import 'jquery-ui/ui/widgets/draggable.js'
import 'jquery-ui/ui/widgets/resizable.js'

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
          content: "마지막 민방위 교육 예비군도 끝나고 민방위도 4년차. ",
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
    var a = $("._item");
    a.draggable({      stop: (eventObj) => {
        console.log('drag eventObj :', eventObj);
      }    });

    a.resizable({
      maxHeight: 300,
      maxWidth: 300,
      minHeight: 80,
      minWidth: 130,
      stop: function (eventObj) {
        console.log('resize eventObj :', eventObj);
      }
    });
  }
};
</script>

<style scoped>
.xs-btn{
  padding:0.15rem;
  font-size:0.75rem;
}
</style>

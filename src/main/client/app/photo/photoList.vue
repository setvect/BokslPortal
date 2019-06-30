<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <!-- <b-input v-model="searchData.word" id="inline-form-input-name" size="sm" placeholder="검색어"></b-input> -->
        <input type="text" class="form-control has-feedback-left _datepicker" placeholder="First Name" readonly="readonly">
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-container fluid class="p-4 bg-dark">
      <b-row>
        <b-col class="photo-img">
          <b-img thumbnail fluid src="https://picsum.photos/250/250/?image=54" alt="Image 1"></b-img>
        </b-col>
        <b-col class="photo-img">
          <b-img thumbnail fluid src="https://picsum.photos/250/250/?image=54" alt="Image 1"></b-img>
        </b-col>
        <b-col class="photo-img">
          <b-img thumbnail fluid src="https://picsum.photos/250/250/?image=58" alt="Image 2"></b-img>
        </b-col>
        <b-col class="photo-img">
          <b-img thumbnail fluid src="https://picsum.photos/250/250/?image=59" alt="Image 3"></b-img>
        </b-col>
        <b-col class="photo-img">
          <b-img thumbnail fluid src="https://picsum.photos/250/250/?image=58" alt="Image 2"></b-img>
        </b-col>
        <b-col class="photo-img">
          <b-img thumbnail fluid src="https://picsum.photos/250/250/?image=59" alt="Image 3"></b-img>
        </b-col>
      </b-row>
    </b-container>
    <b-pagination v-model="searchData.currentPage" :total-rows="page.total" :per-page="page.perPage" @change="changePage" limit="10" align="center" style="padding-top:30px;"/>
    <b-row>
      <b-col style="text-align:right">
        <b-button @click="addPage()" type="button" variant="info">만들기</b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import '../../utils/vue-common.js'
import "daterangepicker";
import 'daterangepicker/daterangepicker.css';
import moment from "moment";

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
          photoSeq: 1,
          content: 'aaa',
          editDate: 1561071320000
        },
        {
          photoSeq: 1,
          content: "복슬이",
          editDate: 1560178320000
        },
      ],
      searchData: {
        from: '20190101',
        to: '20190501',
        currentPage: 1
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
      this.$router.push({ name: "photoAdd" });
    },
    changePage(page) {
      console.log("page :", page);
    }
  },
  mounted() {
    $('._datepicker').daterangepicker({
      singleDatePicker: true,
      singleClasses: "",
      showDropdowns: true,
      startDate: moment(new Date(2016, 7, 12))
    }, (start) => {
      console.log('start.format("YYYY-MM-DD") :', start.format("YYYY-MM-DD"));
    });
  }
};
</script>

<style >
  .photo-img{
    width: 300px;
    flex-basis: auto;
    margin-bottom: 20px;
  }
</style>

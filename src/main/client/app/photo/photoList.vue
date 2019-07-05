<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <input type="text" class="form-control daterange _datepicker" readonly="readonly">
        <b-button variant="primary" size="sm">검색</b-button>
      </b-form>
    </div>
    <b-container fluid class="p-4 bg-dark">
      <b-row>
        <b-col v-for="(image, index) in imageList" :key="image.src" class="photo-img">
          <b-img thumbnail fluid :src="image.src" :title="'image ' + index" @click="show(index)"></b-img>
        </b-col>
      </b-row>
    </b-container>
    <LightBox :images="imageList" ref="imageBox" :show-light-box="false"></LightBox>
    <b-pagination v-model="searchData.currentPage" :total-rows="page.total" :per-page="page.perPage" @change="changePage" limit="10" align="center" style="padding-top:30px;"/>
  </div>
</template>

<script>
import '../../utils/vue-common.js'
import "daterangepicker";
import 'daterangepicker/daterangepicker.css';
import 'vue-image-lightbox/dist/vue-image-lightbox.min.css';
import LightBox from 'vue-image-lightbox'
import moment from "moment";

export default {
  components: {
    LightBox,
  },
  data() {
    return {
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "content", label: "내용", class: 'content-col' },
        { key: "editDate", label: "수정일", class: 'date-col' }
      ],
      imageList: [
        {
          thumb: 'https://picsum.photos/250/250/?image=50',
          src: 'https://picsum.photos/250/250/?image=50',
        },
        {
          thumb: 'https://picsum.photos/250/250/?image=51',
          src: 'https://picsum.photos/250/250/?image=51',
        },
        {
          thumb: 'https://picsum.photos/250/250/?image=52',
          src: 'https://picsum.photos/250/250/?image=52',
        },
        {
          thumb: 'https://picsum.photos/250/250/?image=53',
          src: 'https://picsum.photos/250/250/?image=53',
        },
        {
          thumb: 'https://picsum.photos/250/250/?image=54',
          src: 'https://picsum.photos/250/250/?image=54',
        },
        {
          thumb: 'https://picsum.photos/250/250/?image=55',
          src: 'https://picsum.photos/250/250/?image=55',
        },
        {
          thumb: 'https://picsum.photos/250/250/?image=56',
          src: 'https://picsum.photos/250/250/?image=56',
        },
      ],
      searchData: {
        from: '20190101',
        to: '20190501',
        currentPage: 1
      },
      isShowItem: false,
      page: {
        total: 300,
        perPage: 10
      },
    };
  },
  methods: {
    addPage() {
      this.$router.push({ name: "photoAdd" });
    },
    changePage(page) {
      console.log("page :", page);
    },
    show(index) {
      this.$refs.imageBox.showImage(index);
    }
  },
  mounted() {
    $('._datepicker').daterangepicker({
      showDropdowns: true,
      locale: {
        format: 'YYYY-MM-DD'
      },
      startDate: moment(new Date(2016, 0, 12)),
      endDate: moment(new Date(2016, 7, 31))
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
    flex-grow: unset;
    margin-bottom: 20px;
  }
  .daterange{
    width: 220px !important;
  }
</style>

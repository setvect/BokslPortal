<template>
  <div>
    <div>
      <b-form inline style="display:block; margin-bottom: 10px;">
        <input type="text" class="form-control daterange _datepicker" readonly="readonly" />
        <b-button variant="primary" size="sm" @click="nextProc()">검색</b-button>
      </b-form>
    </div>
    <b-container fluid class="p-4 bg-dark">
      <b-row>
        <b-col v-for="(image, index) in page.list" :key="image.photoId" class="photo-img">
          <b-img thumbnail fluid :src="image.thumb" :title="image.memo" @click="show(index)"></b-img>
        </b-col>
      </b-row>
    </b-container>
    <LightBox :images="page.list" ref="imageBox" :show-light-box="false"></LightBox>
    <div>
      <b-button @click="nextProc()" block variant="outline-secondary" size="sm">더보기({{page.list.length | numberFormat}}/{{page.totalCount | numberFormat}})</b-button>
    </div>
  </div>
</template>

<script>
import '../../utils/vue-common.js'
import "daterangepicker";
import 'daterangepicker/daterangepicker.css';
import 'vue-image-lightbox/dist/vue-image-lightbox.min.css';
import { getToken } from "../../utils/auth.js";
import LightBox from 'vue-image-lightbox'
import moment from "moment";

export default {
  components: {
    LightBox,
  },
  data() {
    return {
      page: {
        list: [],
        totalCount: -1,
      },
      fields: [
        { key: "index", label: "#", class: 'index-col' },
        { key: "content", label: "내용", class: 'content-col' },
        { key: "editDate", label: "수정일", class: 'date-col' }
      ],
      searchParam: {
        from: moment().add('y', -1).valueOf(),
        to: moment().valueOf(),
        startCursor: 0,
      },
      isShowItem: false,
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
      VueUtil.get("/photo/page", this.searchParam, (res) => {
        this.page.totalCount = res.data.totalCount;
        res.data.list.forEach(item => {
          item.thumb = this.getThumbimage(item.photoId);
          item.src = this.getSrc(item.photoId);
        })
        this.page.list = this.page.list.concat(res.data.list);
      });
    },
    show(index) {
      this.$refs.imageBox.showImage(index);
    },
    initDaterange() {
      $('._datepicker').daterangepicker({
        showDropdowns: true,
        locale: {
          format: 'YYYY-MM-DD'
        },
        startDate: new Date(this.searchParam.from),
        endDate: new Date(this.searchParam.to),
      }, (start, end) => {
        this.searchParam.from = start.valueOf();
        this.searchParam.to = end.valueOf();
      });
    },
    getSrc(photoId) {
      let param = {
        photoId: photoId,
      }
      return CommonUtil.appendContextRoot("/photo/orgimage?" + $.param(param));
    },
    getThumbimage(photoId) {
      let param = {
        photoId: photoId,
        w: 330,
        h: 300,
      }
      return CommonUtil.appendContextRoot("/photo/thumbimage?" + $.param(param));
    }
  },
  mounted() {
    console.log('this.page.list :', this.page.list);
    this.initDaterange();
    this.nextProc();
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

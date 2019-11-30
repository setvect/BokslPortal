<template>
  <div>
    <b-row>
      <b-col v-for="item in page.list" :key="item.boardArticleSeq" xl="6" lg="12">
        <b-card :title="item.title" tag="article" style="margin-bottom: 20px;">
          <b-card-img v-if="getImageAttach(item)" :src="getImageUrl(item)" @click="openImage(getImageAttach(item))" width="300" alt="Image" left></b-card-img>
          <b-card-text v-html="item.content"></b-card-text>
          <div style="text-align:right">
            <em>{{item.regDate | relativeDate}}</em>
            <br />
            <b-link @click="editPage(item.boardArticleSeq)">수정</b-link>
            <b-link @click="deleteProc(item.boardArticleSeq)">삭제</b-link>
          </div>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import boardCommon from "./mixin-boardArticle.js";

export default {
  mixins: [comFunction, boardCommon],
  data() {
    return {
    };
  },
  props: {
    page: {
      type: Object
    }
  },
  watch: {
    'page'() {
      this.fitImage(".card-text img");
      this.$nextTick(() => {
        $(".card-text img").on("click", (e) => {
          let imageWidth = e.target.naturalWidth;
          Swal.fire({
            imageUrl: e.target.src,
            width: imageWidth,
            showCloseButton: true
          })
        });
      });
    }
  },
  methods: {
    getImageUrl(item) {
      let attach = this.getImageAttach(item);
      if (attach) {
        return this.getThumUrl(attach, 300, 300);
      }
      return null;
    },
    getImageAttach(item) {
      let images = this.filterImageFiles(item.attach);
      if (images.length == 0) {
        return null;
      }
      return images[0];
    }
  },
  mounted() {
  },
  beforeRouteUpdate(to, from, next) {
  }
};
</script>

<style >
</style>

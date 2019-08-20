<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <vue-dropzone ref="uploadZone" id="dropzone" :options="dropzoneOptions"></vue-dropzone>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button @click="cancel()" type="button" variant="info">취소</b-button>
        </b-col>
        <b-col cols="auto">
          <b-button @click="submitProc()" type="button" variant="info">확인</b-button>
        </b-col>
      </b-row>
    </form>
  </div>
</template>
<script>
import vue2Dropzone from 'vue2-dropzone';
import 'vue2-dropzone/dist/vue2Dropzone.min.css';

export default {
  components: {
    vueDropzone: vue2Dropzone
  },
  data() {
    return {
      dropzoneOptions: {
        url: CommonUtil.appendContextRoot("/"),
        thumbnailWidth: 150,
        maxFilesize: 10,
        dictDefaultMessage: "<i class='fa fa-cloud-upload'></i> 이미지 올려라",
        headers: { "My-Awesome-Header": "header value" },
        autoProcessQueue: false,
        addRemoveLinks: true,
        acceptedFiles: ".psd,.jpg,.jpeg,.png"
      }
    };
  },
  methods: {
    cancel() {
      this.$refs.uploadZone.removeAllFiles();
    },
    submitProc() {
      this.$refs.uploadZone.processQueue();
    },
  },
};
</script>
<style scoped>
</style>
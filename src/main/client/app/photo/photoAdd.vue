<template>
  <div>
    <h5>글 등록</h5>
    <form autocomplete="off">
      <b-form-group>
        <vue-dropzone ref="myVueDropzone" id="dropzone" :options="dropzoneOptions"></vue-dropzone>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button @click="listPage()" type="button" variant="info">취소</b-button>
        </b-col>
        <b-col cols="auto">
          <b-button @click="submitProc()" type="button" variant="info">확인</b-button>
        </b-col>
      </b-row>
    </form>
  </div>
</template>
<script>
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'

export default {
  components: {
    vueDropzone: vue2Dropzone
  },
  data() {
    return {
      dropzoneOptions: {
        url: 'https://127.0.0.1',
        thumbnailWidth: 150,
        maxFilesize: 0.5,
        dictDefaultMessage: "<i class='fa fa-cloud-upload'></i> UPLOAD ME",
        headers: { "My-Awesome-Header": "header value" }
      }
    };
  },
  methods: {
    submitProc() {
      alert('Correct them errors!');
    },
  },
};
</script>
<style scoped>
</style>
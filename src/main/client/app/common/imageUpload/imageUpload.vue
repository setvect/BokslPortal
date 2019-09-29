<template>
  <b-modal ref="imageUploadForm" title="이미지 업로드" @ok.prevent="confirmEvent" @shown="shownEvent" hide-footer>
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <b-form-group label="첨부파일로 업로드" label-for="input-content">
          <b-form-file accept="image/*" v-model="imageFile" />
          <div style="text-align:right; margin-top:10px;">
            <b-button variant="info" size="sm" @click="uploadImage()">확인</b-button>
          </div>
        </b-form-group>
        <b-form-group label="클립보드에서 붙여 넣기" label-for="input-content">
          <canvas @click="attachClipboard()" style="border: 1px solid grey;" id="image_canvas" width="300" height="300"></canvas>
        </b-form-group>
      </b-form>
    </div>
  </b-modal>
</template>

<script>
export default {
  mixins: [comFunction],
  data() {
    return {
      canvas: null,
      ctx: null,
      pasted: false,
      imageFile: null,
    };
  },
  methods: {
    open() {
      this.$refs['imageUploadForm'].show();
    },
    // 입력 창 오픈시
    shownEvent(event) {
      this.imageClipboard();
    },
    uploadImage(event) {
      this.encodeBase64ImageFile(this.imageFile)
        .then((data) => {
          this.attachImage(data);
        });
    },
    encodeBase64ImageFile(image) {
      return new Promise((resolve, reject) => {
        let reader = new FileReader()
        // convert the file to base64 text
        reader.readAsDataURL(image)
        // on reader load somthing...
        reader.onload = (event) => {
          resolve(event.target.result)
        }
        reader.onerror = (error) => {
          reject(error)
        }
      })
    },
    // 클립보드 이미지를 canvas에 붙어 넣을 수 있도록함.
    imageClipboard(autoresize) {
      this.canvas = document.getElementById("image_canvas");
      this.ctx = this.canvas.getContext("2d");
      //handlers
      document.addEventListener('paste', (e) => {
        this.pasteAuto(e);
      }, false);
    },
    //on paste
    pasteAuto(e) {
      if (e.clipboardData) {
        let items = e.clipboardData.items;
        if (!items) {
          return;
        }

        //access data directly
        for (let i = 0; i < items.length; i++) {
          if (items[i].type.indexOf("image") !== -1) {
            //image
            let blob = items[i].getAsFile();
            let URLObj = window.URL || window.webkitURL;
            let source = URLObj.createObjectURL(blob);
            this.pasteCreateImage(source);
          }
        }
        this.pasted = true;
        e.preventDefault();
      }
    },
    //draw pasted image to canvas
    pasteCreateImage(source) {
      let pastedImage = new Image();
      pastedImage.onload = () => {
        this.canvas.width = pastedImage.width;
        this.canvas.height = pastedImage.height;
        this.ctx.drawImage(pastedImage, 0, 0);
      };
      pastedImage.src = source;
    },
    attachClipboard() {
      if (!this.pasted) {
        return;
      }
      let data = $("#image_canvas")[0].toDataURL('image/jpeg');
      this.attachImage(data);
    },
    // 이미지를  편집창에 적용
    attachImage(data) {
      let html = "<img src='" + data + "'/>";
      this.$emit("pasted", html);
      this.$refs['imageUploadForm'].hide();
    }
  },
  mounted() {

  }
};
</script>

<style >
</style>

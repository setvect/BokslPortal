<template>
  <b-modal ref="nodeForm" title="노드 추가" @ok.prevent="addNodeProc()">
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <b-form-group label="레이블" label-for="input-label">
          <b-form-input v-model="label" name="label" id="input-label" data-vv-as="레이블 " :state="validateState('label')" v-validate="{ required: true, max:30}" />
          <span v-show="!validateState('label')" class="invalid-feedback">{{ veeErrors.first('label') }}</span>
        </b-form-group>
        <b-form-group label="모양" id="input-shape">
          <b-form-radio-group>
            <b-form-radio value="1">타원</b-form-radio>
            <b-form-radio value="2">사각형</b-form-radio>
            <b-form-radio value="3">원</b-form-radio>
          </b-form-radio-group>
        </b-form-group>
        <b-form-group label="색" id="input-color">
          <b-form-radio-group>
            <b-form-radio value="1">
              <span class="color_label" style="background: #ffffcc;" />
            </b-form-radio>
            <b-form-radio value="#ffffff">
              <span class="color_label" style="background: #ffffff;" />
            </b-form-radio>
            <b-form-radio value="#ffff66">
              <span class="color_label" style="background: #ffff66;" />
            </b-form-radio>
            <b-form-radio value="#ccff66">
              <span class="color_label" style="background: #ccff66;" />
            </b-form-radio>
            <b-form-radio value="#99ccff">
              <span class="color_label" style="background: #99ccff;" />
            </b-form-radio>
            <b-form-radio value="#ff99ff">
              <span class="color_label" style="background: #ff99ff;" />
            </b-form-radio>
            <b-form-radio value="#eeeeee">
              <span class="color_label" style="background: #eeeeee;" />
            </b-form-radio>
          </b-form-radio-group>
        </b-form-group>
      </b-form>
    </div>
  </b-modal>
</template>
<script>
import comFunction from "../commonFunction.js";

export default {
  mixins: [comFunction],
  data() {
    return {
      label: "레이블"
    };
  },
  methods: {
    show(){
      console.log("show......");
      this.$refs['nodeForm'].show()
    },
    addNodeProc() {
      console.log('addNodeProc111111111111');
      this.$validator.validateAll().then((result) => {
        console.log('result :', result);
        if (!result) {
          console.log("cancel");
          return;
        }
        console.log("ok");
      });
    },
  },
  mounted() {
  },
};
</script>
<style scoped>
  .color_label {
    border: 1px solid #CCC;
    padding: 10px;
    display: inline-block;
  }
</style>

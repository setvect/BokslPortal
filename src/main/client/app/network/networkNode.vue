<template>
  <b-modal ref="nodeForm" title="노드 추가" @ok.prevent="confirm()">
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <b-form-group label="레이블" label-for="input-label">
          <b-form-input v-model="node.label" name="label" id="input-label" data-vv-as="레이블 " :state="validateState('label')" v-validate="{ required: true, max:30}" />
          <span v-show="!validateState('label')" class="invalid-feedback">{{ veeErrors.first('label') }}</span>
        </b-form-group>
        <b-form-group label="모양" id="input-shape">
          <b-form-radio-group v-model="node.shape" :options="shapeOption" />
        </b-form-group>
        <b-form-group label="색" id="input-color">
          <b-form-radio-group v-model="node.color">
            <b-form-radio v-for="color in colorOption" :key="color" :value="color">
              <span class="color_label" :style="{'background': color}" />
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
      node: {},
      shapeOption: [
        { text: '타원', value: 'ellipse' },
        { text: '사각형', value: 'box' },
        { text: '원', value: 'circle' },
      ],
      colorOption: ["#ffffcc", "#ffffff", "#ffff66", "#ccff66", "#99ccff", "#ff99ff", "#eeeeee",],
      update: false
    };
  },
  methods: {
    show(node) {
      if (node) {
        // node 값이 있으면 수정
        this.node = node;
        this.update = true;
      } else {
        // 신규 추가인 경우 임의 랜덤 값을 아이디로 함
        this.node = {
          id: (Math.random() * 1e7).toString(32),
          label: "",
          shape: this.shapeOption[0].value,
          color: this.colorOption[0],
        }
        this.update = false;
      }
      this.$refs['nodeForm'].show();
    },
    confirm() {
      this.$validator.validateAll().then((result) => {
        if (!result) {
          return;
        }
        this.$refs['nodeForm'].hide();
        if(this.update){
          this.$parent.editNode(this.node);
        }else{
          this.$parent.addNode(this.node);
        }
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

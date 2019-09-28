<template>
  <b-modal ref="passwordChangeForm" title="비밀번호 변경" @ok.prevent="confirmEvent" @shown="shownAddEvent">
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <b-form-group label="지금 비밀번호" label-for="input-content">
          <b-form-input
            ref="content-input"
            v-model="item.currentPassword"
            type="password"
            name="currentPassword"
            v-validate="{ required: true}"
            :state="validateState('currentPassword')"
            id="input-currentPassword"
            data-vv-as="내용 "
          ></b-form-input>
          <span v-show="!validateState('currentPassword')" class="invalid-feedback">{{ veeErrors.first('currentPassword') }}</span>
        </b-form-group>

        <b-form-group label="변경 비밀번호" label-for="input-content">
          <b-form-input
            v-model="item.changePassword"
            type="password"
            name="changePassword"
            v-validate="{ required: true, min: 4}"
            :state="validateState('changePassword')"
            id="input-changePassword"
            data-vv-as="내용 "
          ></b-form-input>
          <span v-show="!validateState('changePassword')" class="invalid-feedback">{{ veeErrors.first('changePassword') }}</span>
        </b-form-group>

        <b-form-group label="비밀번호 확인" label-for="input-content">
          <b-form-input
            v-model="item.comparePassword"
            name="comparePassword"
            type="password"
            v-validate="{ required: true, confirmed: item.changePassword}"
            :state="validateState('comparePassword')"
            id="input-comparePassword"
            data-vv-as="내용 "
          ></b-form-input>
          <span v-show="!validateState('comparePassword')" class="invalid-feedback">{{ veeErrors.first('comparePassword') }}</span>
        </b-form-group>
      </b-form>
    </div>
  </b-modal>
</template>

<script>
import '../utils/vue-common.js'
import _ from "lodash";

export default {
  mixins: [comFunction],
  data() {
    return {
      item: {
        currentPassword: '',
        changePassword: '',
        comparePassword: '',
      },
    };
  },
  props: ["page"],
  methods: {
    open() {
      this.item = { content: "" };
      this.$refs['passwordChangeForm'].show();
    },
    confirmEvent(event) {
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }
        VueUtil.patch("/user/user-change-password", this.item, (res) => {
          this.$refs['passwordChangeForm'].hide();
          alert("비밀번호 변경했다.");
        });
      });
    },
    // 입력 창 오픈시
    shownAddEvent(event) {
      this.$refs['content-input'].focus();
    },
  },
  mounted() {
  }
};
</script>

<style >
</style>

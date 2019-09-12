<template>
  <b-modal ref="todoForm" title="할일 만들기" @ok.prevent="confirmEvent" @shown="shownAddEvent">
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <b-form-group label="내용" label-for="input-content">
          <b-form-input
            ref="content-input"
            @keypress.13="confirmEvent"
            v-model="item.content"
            name="content"
            v-validate="{ required: true}"
            state="validateState('content')"
            id="input-content"
            data-vv-as="내용 "
          ></b-form-input>
          <span v-show="!validateState('content')" class="invalid-feedback">{{ veeErrors.first('content') }}</span>
        </b-form-group>
      </b-form>
    </div>
  </b-modal>
</template>

<script>
import '../../../utils/vue-common.js'
import _ from "lodash";

export default {
  mixins: [comFunction],
  data() {
    return {
      item: {
        content: '',
      },
      // 쓰기 상태, 수정 상태 여부
      confirmEvent: {},
    };
  },
  props: ["page"],
  methods: {
    openAdd() {
      this.item = { content: "" };
      this.$refs['todoForm'].show();
      this.confirmEvent = this.addProc;
    },
    openEdit(item) {
      this.item = item;
      this.$refs['todoForm'].show()
      this.confirmEvent = this.editProc;
    },
    addProc(event) {
      this.$validator.validate().then((result) => {
        if (!result) {
          return;
        }
        VueUtil.post("/todo/item", this.item, (res) => {
          this.$refs['todoForm'].hide();
          this.page.list.unshift(res.data);
        });
      });
    },
    editProc(event) {
      this.$validator.validateAll().then((result) => {
        if (!result) {
          return;
        }
        VueUtil.put("/todo/item", this.item, (res) => {
          let idx = _.findIndex(this.page.list, (ele) => ele.todoSeq === this.item.todoSeq);
          this.page.list.splice(idx, 1, res.data);
          this.$refs['todoForm'].hide();
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

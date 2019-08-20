<template>
  <b-modal ref="edgeForm" title="연결선 추가" @ok.prevent="confirm()" @shown="shownModal">
    <div>
      <b-form autocomplete="off" @submit.stop.prevent>
        <b-form-group label="레이블" label-for="input-label">
          <b-form-input v-model="edge.label" ref="input-label" name="label" id="input-label" data-vv-as="레이블 " :state="validateState('label')" v-validate="{ max:30}" />
          <span v-show="!validateState('label')" class="invalid-feedback">{{ veeErrors.first('label') }}</span>
        </b-form-group>
        <b-form-group label="시작">
          <b-form-select size="sm" name="from" v-model="edge.from" data-vv-as="시작 " :state="validateState('from')" v-validate="{required: true}">
            <option :value="null">--선택--</option>
            <option v-for="node in nodes" :key="node.id" :value="node.id">{{node.label}}</option>
          </b-form-select>
          <span v-show="!validateState('from')" class="invalid-feedback">{{ veeErrors.first('from') }}</span>
        </b-form-group>
        <b-form-group label="끝">
          <b-form-select size="sm" name="to" v-model="edge.to" data-vv-as="시작 " :state="validateState('to')" v-validate="{required: true}">
            <option :value="null">--선택--</option>
            <option v-for="node in nodes" :key="node.id" :value="node.id">{{node.label}}</option>
          </b-form-select>
          <span v-show="!validateState('to')" class="invalid-feedback">{{ veeErrors.first('to') }}</span>
        </b-form-group>
        <b-form-group label="선 형태" id="input-shape">
          <b-form-radio-group :options="dashesOption" v-model="edge.dashes" />
        </b-form-group>
        <b-form-group label="색" id="input-color">
          <b-form-radio-group v-model="edge.color.color">
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
export default {
  mixins: [comFunction],
  data() {
    return {
      edge: { color: {} },
      nodes: [],
      dashesOption: [
        { text: '실선', value: false },
        { text: '점섬', value: true },
      ],
      colorOption: ["#777777", "#006699", "#00ff00", "#aa44aa", "#cc9900", "#cc0066", "#3333ff",],
      update: false
    };
  },
  methods: {
    show(nodes, edge, fromId) {
      this.nodes = nodes;
      nodes.sort((a, b) => ('' + a.label).localeCompare(b.label));
      if (edge) {
        // edge 값이 있으면 수정
        this.edge = edge;
        this.update = true;
      } else {
        // 신규 추가인 경우 임의 랜덤 값을 아이디로 함
        this.edge = {
          id: (Math.random() * 1e7).toString(32),
          from: fromId,
          to: null,
          label: "",
          dashes: this.dashesOption[0].value,
          color: { color: this.colorOption[0] },
        }
        this.update = false;
      }
      this.$refs['edgeForm'].show();
    },
    shownModal() {
      this.$refs['input-label'].focus();
    },
    confirm() {
      this.$validator.validateAll().then((result) => {
        if (!result) {
          return;
        }
        this.$refs['edgeForm'].hide();
        this.edge.color['highlight'] = this.edge.color.color;
        if (this.update) {
          this.$parent.editEdge(this.edge);
        } else {
          this.$parent.addEdge(this.edge);
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

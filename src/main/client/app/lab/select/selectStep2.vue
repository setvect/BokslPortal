<template>
  <div>
    <h5>선택의 시간</h5>
    <form autocomplete="off">
      <b-row>
        <b-col>
          <b-button @click="run()" type="button" variant="info">돌리기</b-button>
          <b-button @click="back()" type="button" variant="info" style="float:right">돌아가기</b-button>
        </b-col>
      </b-row>
    </form>
    <div class="the_wheel" style="height: 582px; width: 438px; padding: 74px 0 0 2px;">
      <canvas id="canvas" width="434" height="434"></canvas>
    </div>
  </div>
</template>
<script>
import Winwheel from '../../../assert/lib/Winwheel/Winwhee.js';
import { TweenMax, Power2, TimelineLite } from "gsap/TweenMax";
import { debuglog } from 'util';
const COLORS = ["#eae56f", "#89f26e", "#7de6ef", "#e7706f", "#fcafee",
  "#89f26e", "#7de6ef", "#e7706f", "#ff3377", "#47d147", "#3399ff", "#ffd633"];

export default {
  data() {
    return {
      wheelSpinning: false,
      theWheel: null,
      menu: "짜장면\n떡볶이\n굶기\n순대국\n됀장찌개",
    };
  },
  methods: {
    init() {
      let colorIdx = 0;
      let menuSplit = this.menu.split("\n");
      let segments = menuSplit.map((m) => {
        if (colorIdx >= COLORS.length) {
          colorIdx = 0;
        }
        return { 'fillStyle': COLORS[colorIdx++], 'text': m };
      });

      console.log('segments :', segments);

      this.theWheel = new Winwheel({
        'numSegments': segments.length,
        'outerRadius': 212,
        'textFontSize': 28,
        'segments': segments,
        'animation':
        {
          'type': 'spinToStop',
          'duration': 5,
          'spins': segments.length,
          // TODO 종료 이벤트가 발생하지 않는다.
          'callbackFinished': (indicatedSegment) => {console.log('indicatedSegment :', indicatedSegment.text); }
        }
      });
    },
    run() {
      if (this.wheelSpinning) {
        return;
      }
      this.theWheel.startAnimation();
      this.wheelSpinning = true;
    },
    back() {
      this.$router.push({ name: "select-step1" });
    },
  },
  mounted() {
    console.log('Winwheel :', Winwheel);
    this.init();
  }
};
</script>
<style scoped>
.the_wheel{
  background-image: url("../../../assert/image/wheel_back.png");
  background-position: center;
}
</style>
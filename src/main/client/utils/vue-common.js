import Vue from "vue";

// 숫자 (,)콤마 추가
Vue.filter("numberFormat", value => {
  if (value === undefined) {
    return null;
  }
  return value.toLocaleString();
});

// 날짜 포맷 변환
// moment format pattern
Vue.filter("dateFormat", (value, format) => {
  if (moment.isMoment(value)) {
    return value.format(format);
  }
  if (value instanceof Date) {
    return moment(value).format(format);
  }
  if (!isNaN(value)) {
    return moment(value).format(format);
  }
  return moment().format(format);
});

// 상대적 날짜 표시
Vue.filter("relativeDate", (value) => {
  let date;
  if (moment.isMoment(value)) {
    date = value;
  } else if (value instanceof Date) {
    date = moment(value);
  } else if (!isNaN(value)) {
    date = moment(value);
  } else {
    date = moment();
  }
  let itemDate = date.valueOf();;

  let current = (new Date()).getTime()
  var delta = Math.round((current - itemDate) / 1000);
  var minute = 60,
    hour = minute * 60,
    day = hour * 24;

  var rtnValue;
  if (delta < 30) {
    rtnValue = delta + '초 전';
  } else if (delta < hour) {
    rtnValue = Math.floor(delta / minute) + '분 전';
  } else if (delta < day) {
    rtnValue = Math.floor(delta / hour) + '시간 전';
  } else if (delta < day * 100) {
    rtnValue = Math.floor(delta / day) + '일 전';
  } else {
    rtnValue = date.format("YYYY-MM-DD");
  }
  return rtnValue;
});

// 목록 번호 계산. 내림차순(높은 번호 부터)으로 표시
Vue.filter("indexSeq", (index, page) => page.totalCount - page.startCursor - index);

/*
 * 전역적으로 사용할 디렉티브 정의
 */

// 줄바꿈 -> br 태그 적용
Vue.directive("br", {
  bind: (el, binding) => {
    $(el).html(CommonUtil.toBr(binding.value));
  }
});

// 컴포넌트(component) 선언

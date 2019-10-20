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

// 목록 번호 계산. 내림차순(높은 번호 부터)으로 표시
Vue.filter("indexSeq", (index, page) => page.totalCount - page.startCursor - index );

/*
 * 전역적으로 사용할 디렉티브 정의
 */

// 줄바꿈 -> br 태그 적용
Vue.directive("br", {
  update: (el, binding) => {
    $(el).html(CommonUtil.toBr(binding.value));
  }
});

// 컴포넌트(component) 선언

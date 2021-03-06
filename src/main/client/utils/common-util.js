// 프로그램 전역적으로 사용하는 공통 함수
var CommonUtil = {}

/**
 * 프로그램 오류로 인한 경고창
 */
CommonUtil.popupError = function (err) {
  var message = err.response == null ? err.message : err.response.data.message
  if (err.message != null) {
    console.log("프로그램 에러", message)
    Swal.fire('에러다', message, 'error');
  } else {
    console.log("프로그램 에러", err)
    Swal.fire('에러다', err, 'error')
  }
}

/**
 * twbsPagination 페이징 처리 관련 옵션
 */
CommonUtil.makePageOption = function (page, callback) {
  return {
    initiateStartPageClick: false,
    totalPages: page.totalPage === 0 ? 1 : page.totalPage,
    visiblePages: page.visiblePage,
    startPage: page.currentPage,
    first: null,
    last: null,
    prev: "«",
    next: "»",
    href: "javascript:void(0)",
    onPageClick: callback
  }
}

CommonUtil.clearHtml = function (html) {
  if (!html) {
    return
  }
  return html.replace(/<\/?[^>]+(>|$)/g, "");
}

// 기존 페이징 객체 제거
CommonUtil.destroyPage = function (selector) {
  if ($(selector).data("twbs-pagination")) {
    $(selector).twbsPagination("destroy")
  }
}

// 콤마
CommonUtil.toComma = function (value) {
  return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
}

// 줄바꿈을 br 테그로 변경
CommonUtil.toBr = function (text) {
  if (text === undefined || text == null) {
    return null
  }
  return text.replace(/(?:\r\n|\r|\n)/g, "<br/>")
}

// null 또는 빈 공백이면 true 반환
CommonUtil.isEmpty = function (val) {
  return val === undefined || val == null || val.length <= 0 || val == "";
}

// 섬네일 만들 수 있는 이미지 파일 확장자 체크
CommonUtil.isImage = function(filename){
  return (/\.(gif|jpg|jpeg|png)$/i).test(filename)
}

// 공백제거
CommonUtil.removeWhiteSpace = function (val) {
  if (CommonUtil.isEmpty(val)) {
    return ""
  } else {
    return val.replace(/\s/gi, "")
  }
}

// context root path
CommonUtil.getContextPath = function () {
  return $("meta[name='contextRoot']").attr("content") || ""
}

CommonUtil.appendContextRoot = function (url) {
  return CommonUtil.getContextPath() + url
}

// 정규표현식에서 사용하는 특수문자를 escape 처리함
CommonUtil.escapeRegExp = function (str) {
  return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&")
}

export default CommonUtil

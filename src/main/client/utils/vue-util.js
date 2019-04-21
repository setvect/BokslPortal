import CommonUtil from "./common-util.js"
import axios from "axios"
import $ from "jquery"

// Vue 관련 공통 함수.
const VueUtil = {}
const NOTING_OPERATION = () => {}
/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.get = function(url, param, callback, option) {
  VueUtil._ajaxCall("get", url, param, callback, option)
}

/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.post = function(url, param, callback, option) {
  VueUtil._ajaxCall("post", url, param, callback, option)
}

/*
 * get, post 처리
 */
VueUtil._ajaxCall = function(callType, url, _param, _callback, _option) {
  const param = _param || {}
  const callback = _callback || NOTING_OPERATION
  const option = _option || {}

  let axiosMethod
  let sendParam
  if (callType === "get") {
    axiosMethod = axios.get
    const paramParsing = option.paramParsing || false
    if (paramParsing) {
      const searchParams = new URLSearchParams($.param(param, true))
      sendParam = { params: searchParams }
    } else {
      sendParam = { params: param }
    }
  } else if (callType === "post") {
    axiosMethod = axios.post
    sendParam = $.param(param, true)
  }

  const finallyCall = option.finallyCall || NOTING_OPERATION
  const errorCall =
    option.errorCall ||
    function(err) {
      CommonUtil.popupError(err)
    }

  if (option.waitDialog !== false) {
    // waitDialog.show(waitMsg, { dialogSize: "sm" })
  }

  axiosMethod(CommonUtil.appendContextRoot(url), sendParam)
    .then(result => callback(result))
    .catch(err => errorCall(err))
    .finally(() => {
      if (option.waitDialog !== false) {
        // waitDialog.hide()
      }
      finallyCall()
    })
}

export default VueUtil

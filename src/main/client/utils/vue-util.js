import CommonUtil from "./common-util.js"
import axios from "axios"
import $ from "jquery"
import store from "../store/index"
import {
  getToken
} from "./auth.js"

// Vue 관련 공통 함수.
const VueUtil = {}
const NOTING_OPERATION = () => {}
/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.get = function (url, param, callback, option) {
  VueUtil._ajaxCall("get", url, param, callback, option)
}

/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.post = function (url, param, callback, option) {
  VueUtil._ajaxCall("post", url, param, callback, option)
}

/*
 * url: 호출 주소 param: 전달 파라미터(JSON 문자열) option: 옵션
 */
VueUtil.json = function (url, param, callback, option) {
  VueUtil._axisCall("json", url, param, callback, option)
}

/*
 * url: 호출 주소 param: 전달 파라미터 option: 옵션
 */
VueUtil.multipart = function (url, param, callback, option) {
  VueUtil._axisCall("multipart", url, param, callback, option)
}

/*
 * get, post 처리
 */
VueUtil._ajaxCall = function (callType, url, _param, _callback, _option) {
  const param = _param || {}
  const callback = _callback || NOTING_OPERATION
  const option = _option || {}
  const config = {
    headers: {}
  }
  if (store.getters.token) {
    // console.log("getToken() :", getToken())
    // 인증 토큰
    axios.defaults.headers.common["x-auth-token"] = getToken()
  }

  let axiosMethod;
  let sendParam;

  if (callType === "get") {
    var paramValue = new URLSearchParams();
    Object.keys(param).map((key) => {
      let value = param[key];
      if (value === null) {
        return;
      }
      if (Array.isArray(value)) {
        value.forEach((v) => paramValue.append(key, v));
      } else {
        paramValue.append(key, param[key]);
      }
    })

    axiosMethod = axios.get;
    sendParam = {
      params: paramValue
    }
  } else if (callType === "post") {
    axiosMethod = axios.post
    sendParam = $.param(param, true)
  } else if (callType === "json") {
    axiosMethod = axios.post
    sendParam = param
    config.headers["Content-Type"] = "application/json; charset=utf-8"
  } else if (callType === "multipart") {
    axiosMethod = axios.post
    sendParam = param
    config.headers["Content-Type"] = "multipart/form-data"
  }

  const finallyCall = option.finallyCall || NOTING_OPERATION
  const errorCall =
    option.errorCall ||
    function (err) {
      CommonUtil.popupError(err)
    }

  axiosMethod(CommonUtil.appendContextRoot(url), sendParam, config)
    .then(result => callback(result))
    .catch(err => errorCall(err))
    .finally(() => finallyCall())
}

export default VueUtil

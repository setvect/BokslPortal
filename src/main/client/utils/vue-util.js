import CommonUtil from "./common-util.js";
import axios from "axios";
import $ from "jquery";
import store from "../store/index";
import { getToken } from "./auth.js";

// Vue 관련 공통 함수.
const VueUtil = {};
const NOTING_OPERATION = () => {};
/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.get = function(url, param, callback, option) {
  VueUtil._ajaxCall("get", url, param, callback, option);
};

/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.post = function(url, param, callback, option) {
  VueUtil._ajaxCall("post", url, param, callback, option);
};

/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.put = function(url, param, callback, option) {
  VueUtil._ajaxCall("put", url, param, callback, option);
};

/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.patch = function(url, param, callback, option) {
  VueUtil._ajaxCall("patch", url, param, callback, option);
};

/*
 * url: 호출 주소
 * param: 전달 파라미터
 * option: 옵션
 */
VueUtil.delete = function(url, param, callback, option) {
  VueUtil._ajaxCall("delete", url, param, callback, option);
};

/*
 * get, post 처리
 */
VueUtil._ajaxCall = function(method, url, _param, _callback, _option) {
  const param = _param || {};
  const callback = _callback || NOTING_OPERATION;
  const option = _option || {};
  const config = {
    headers: {}
  };
  if (store.getters.token) {
    // 인증 토큰
    axios.defaults.headers.common["x-auth-token"] = getToken();
  }

  let methodMap = {
    get: axios.get,
    post: axios.post,
    put: axios.put,
    patch: axios.patch,
    delete: axios.delete
  };

  let callType = option["call-type"];

  let axiosMethod = methodMap[method];
  let sendParam;

  if (method === "get") {
    var paramValue = new URLSearchParams();
    Object.keys(param).map(key => {
      let value = param[key];
      if (value === null) {
        return;
      }
      if (Array.isArray(value)) {
        value.forEach(v => paramValue.append(key, v));
      } else {
        paramValue.append(key, param[key]);
      }
    });
  } else {
    if (callType === "json") {
      sendParam = param;
      config.headers["Content-Type"] = "application/json; charset=utf-8";
    } else if (callType === "multipart") {
      sendParam = param;
      config.headers["Content-Type"] = "multipart/form-data";
    } else {
      sendParam = $.param(param, true);
    }
  }

  const finallyCall = option.finallyCall || NOTING_OPERATION;
  const errorCall =
    option.errorCall ||
    function(err) {
      CommonUtil.popupError(err);
    };

  axiosMethod(CommonUtil.appendContextRoot(url), sendParam, config)
    .then(result => callback(result))
    .catch(err => errorCall(err))
    .finally(() => finallyCall());
};

export default VueUtil;

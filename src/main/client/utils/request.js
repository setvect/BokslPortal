import axios from "axios"
import { Message, MessageBox } from "element-ui"
import store from "../store"
import { getToken } from "@/utils/auth"

const service = axios.create({
  baseURL: process.env.BASE_API,
  timeout: 5000
})

// request 인터셉터 요청
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers["X-Token"] = getToken() // 인증토큰
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response
service.interceptors.response.use(
  response => {
    /**
     * 코드는 20000이 아니며 실수는 자신의 비즈니스와 결합하여 수정할 수 있습니다.
     */
    const res = response.data
    if (res.code !== 20000) {
      Message({
        message: res.message,
        type: "error",
        duration: 5 * 1000
      })

      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        MessageBox.confirm("你已被登出，可以取消继续留在该页面，或者重新登录", "确定登出", {
          confirmButtonText: "重新登录",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          store.dispatch("FedLogOut").then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      return Promise.reject("error")
    } else {
      return response.data
    }
  },
  error => {
    console.log("err" + error) // for debug
    Message({
      message: error.message,
      type: "error",
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service

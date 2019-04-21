import { logout, getInfo } from "@/api/login"
import { getToken, setToken, removeToken } from "@/utils/auth"
import VueUtil from "@/utils/vue-util"

const user = {
  state: {
    token: getToken(),
    name: "",
    avatar: "",
    roles: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 로그인
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        VueUtil.post(
          "/user/login.do",
          {
            username: userInfo.username,
            password: userInfo.password
          },
          result => {
            const data = result.data
            setToken(data.token)
            commit("SET_TOKEN", data.token)
            resolve()
          },
          {
            errorCall: error => {
              reject(error)
            }
          }
        )
      })
    },

    // 사용자 정보
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token)
          .then(response => {
            const data = response.data
            if (data.roles && data.roles.length > 0) {
              // 주어진 role 검사
              commit("SET_ROLES", data.roles)
            } else {
              reject("getInfo: roles must be a non-null array !")
            }
            commit("SET_NAME", data.name)
            commit("SET_AVATAR", data.avatar)
            resolve(response)
          })
          .catch(error => {
            reject(error)
          })
      })
    },

    // 로그아웃
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token)
          .then(() => {
            commit("SET_TOKEN", "")
            commit("SET_ROLES", [])
            removeToken()
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },

    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit("SET_TOKEN", "")
        removeToken()
        resolve()
      })
    }
  }
}

export default user

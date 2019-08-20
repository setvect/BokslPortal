import {
  getToken,
  setToken,
  removeToken
} from "../../utils/auth.js"

const user = {
  state: {
    token: getToken(),
    name: "",
    roles: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },

  actions: {
    // 로그인
    Login({
      commit
    }, userInfo) {
      return new Promise((resolve, reject) => {
        VueUtil.post(
          "/user/login", {
            username: userInfo.username,
            password: userInfo.password,
            'remember-me': userInfo['remember-me']
          },
          result => {
            const data = result.data
            setToken(data.token)
            commit("SET_TOKEN", data.token)
            resolve()
          }, {
            errorCall: error => {
              reject(error)
            }
          }
        )
      })
    },
    // 사용자 정보
    GetInfo({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        VueUtil.get(
          "/user/info.json", {
            token: state.token
          },
          result => {
            const data = result.data
            const role = data.userRole.map(r => r.roleName)
            if (role.length > 0) {
              // 주어진 role 검사
              commit("SET_ROLES", role)
            } else {
              reject("getInfo: roles must be a non-null array !")
            }
            commit("SET_NAME", data.name)
            resolve(result)
          }, {
            errorCall: error => {
              reject(error)
            }
          }
        )
      })
    },
    // 로그아웃
    LogOut({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        VueUtil.post(
          "/user/logout", {},
          result => {
            commit("SET_TOKEN", "")
            commit("SET_ROLES", [])
            removeToken()
            resolve()
          }, {
            errorCall: error => {
              reject(error)
            }
          }
        )
      })
    },
    FedLogOut({
      commit
    }) {
      return new Promise(resolve => {
        commit("SET_TOKEN", "")
        removeToken()
        resolve()
      })
    }
  }
}

export default user

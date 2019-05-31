import Vue from "vue"
import Vuex from "vuex"
import user from "./modules/user"

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user
  },
  getters: {
    token: state => state.user.token,
    name: state => state.user.name,
    roles: state => state.user.roles
  }
})

export default store

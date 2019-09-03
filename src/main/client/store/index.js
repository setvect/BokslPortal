import Vue from "vue"
import Vuex from "vuex"
import user from "./modules/user.js"
import memo from "./modules/memo.js"
import note from "./modules/note.js"

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user, memo, note
  },
  getters: {
    token: state => state.user.token,
    name: state => state.user.name,
    roles: state => state.user.roles
  }
})

export default store

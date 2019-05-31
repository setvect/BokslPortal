import router from './router.js'
import store from './store/index.js'
import { getToken } from './utils/auth' // getToken from cookie

const whiteList = ['/login'] // 로그인 안해도 접근 가능한 페이지
router.beforeEach((to, from, next) => {
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      if (store.getters.roles.length === 0) {
        store.dispatch('GetInfo').then(res => { // 사용자 정보 가져옴
          next()
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            alert(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 로그인페이지로 리다리렉션
    }
  }
})

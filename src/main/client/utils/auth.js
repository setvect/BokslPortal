import Cookies from 'js-cookie'

const TokenKey = 'boksl_portal_token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token, isRememberMe) {
  let option = isRememberMe ? {
    expires: 20 // 20 days
  } : null;
  return Cookies.set(TokenKey, token, option)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

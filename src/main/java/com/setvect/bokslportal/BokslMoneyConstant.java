package com.setvect.bokslportal;

/**
 * 상수 정의.
 */
public final class BokslMoneyConstant {
  /**
   * not instance.
   */
  private BokslMoneyConstant() {
  }

  /**
   * js, css 등 웹 자원 파일 캐싱을 하지위한 값<br>
   * src="${request.contextPath}/boksl.js?<%=com.setvect.bokslmoney.BokslMoneyConstant.CACHE_VER%>"
   */
  public static final long CACHE_VER = System.currentTimeMillis();

  /**
   * 로그인 관련 상수.
   */
  public static class Login {
    /**
     * 로그인 아이디(고정)
     */
    public static final String ID = "boksl";
    /**
     * remember 관련.
     */
    public static final String REMEMBER_ME_KEY = "bokslLoginKey";
    /**
     * remember 관련.
     */
    public static final String REMEMBER_COOKIE_NAME = "bokslCookie";

  }
}

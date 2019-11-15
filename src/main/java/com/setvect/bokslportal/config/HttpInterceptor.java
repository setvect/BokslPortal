package com.setvect.bokslportal.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http 호출 시 전, 후 처리 로직
 */
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

  /**
   * Application 시작과 동시에 최초 한번 실행.
   */
  @PostConstruct
  public void init() {
  }

  @Override
  public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
    return true;
  }
}

package com.setvect.bokslportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * request 요청 시 공통으로 처리할 로직 넣을 때 사용.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * HandlerInterceptor.
   */
  @Autowired
  private HandlerInterceptor interceptor;

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(interceptor);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/app/js/**", "/asserts/**")
      .addResourceLocations("/app/js/", "/asserts/")
      .setCacheControl(CacheControl.maxAge(10, TimeUnit.DAYS));
  }
}

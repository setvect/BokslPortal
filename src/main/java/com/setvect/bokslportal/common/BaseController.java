package com.setvect.bokslportal.common;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 특정 타입에 대한 전역 바인드 처리
 */
@ControllerAdvice
public class BaseController {
  /**
   * Date 타입 속성을 바인드
   *
   * @param binder
   *          WebDataBinder
   */
  @InitBinder
  public void initBinder(final WebDataBinder binder) {
    binder.registerCustomEditor(Date.class, new DatePropertyEditor());
  }
}

package com.setvect.bokslportal.common.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 전역 예외 처리.
 */
@ControllerAdvice
@RestController
@Log4j2
public class ExceptionHandlingController {

  /**
   * 공통 예외 처리.
   *
   * @param exception 전달 받은 예외 객체
   * @return 예외 처리 페이지
   * @throws IOException .
   */
  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(final Exception exception)  {
    ModelAndView mav = new ModelAndView();
    log.error(exception.getMessage(), exception);
    mav.addObject("message", exception.getMessage());
    mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    mav.setViewName("error");
    return mav;
  }
}

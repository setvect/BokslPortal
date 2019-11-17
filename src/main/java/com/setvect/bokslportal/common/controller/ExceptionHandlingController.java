package com.setvect.bokslportal.common.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
   * @param res       servletResponse
   * @param exception 전달 받은 예외 객체
   * @return 예외 처리 페이지
   * @throws IOException .
   */
  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(final HttpServletResponse res, final Exception exception) throws IOException {
    ModelAndView mav = new ModelAndView();
    log.error(exception.getMessage(), exception);
    res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
    return mav;
  }

//  /**
//   * 공통 예외 처리.
//   *
//   * @param exception 전달 받은 예외 객체
//   * @return 예외 처리 페이지
//   * @throws IOException .
//   */
//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//  @ResponseBody
//  public ApplicationErrorDto handleError(final Exception exception){
//    ApplicationErrorDto applicationErrorDto = new ApplicationErrorDto();
//    applicationErrorDto.setMessage(exception.getMessage());
//    log.error(exception.getMessage(), exception);
//    return applicationErrorDto;
//  }
//
//  @Getter
//  @Setter
//  public static class ApplicationErrorDto {
//    private String message;
//  }
}

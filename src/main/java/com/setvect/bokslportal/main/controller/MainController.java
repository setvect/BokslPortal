package com.setvect.bokslportal.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String page(final HttpServletRequest request) {
    int a = 0;
    if (a == 0) {
      throw new RuntimeException("aaaaaaaaaaaaaaaaaaaaa");
    }
    return "index";
  }

  /**
   * 로그인 화면. SecurityConfig 설정과 연계
   *
   * @return view 페이지
   */
  @RequestMapping("/login")
  public String login() {
    return "login";
  }
}

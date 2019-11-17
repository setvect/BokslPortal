package com.setvect.bokslportal.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String page(final HttpServletRequest request) {
    return "index";
  }
}

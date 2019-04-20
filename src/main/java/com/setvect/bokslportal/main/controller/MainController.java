package com.setvect.bokslportal.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String page(final HttpServletRequest request) {
    return "index";
  }

  @RequestMapping(value = "/test.json", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> test() {
    Map<String, Object> v = new HashMap<>();
    v.put("a", 123);
    v.put("b", 456);
    return v;
  }
}

package com.setvect.bokslportal.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dummy")
public class DummyController {
  @RequestMapping(value = "/table.json", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<List<Map<String, Object>>> test() {

    List<Map<String, Object>> result = new ArrayList<>();

    Map<String, Object> item = new HashMap<>();
    item.put("author", "복슬이");
    item.put("id", "121212");
    item.put("display_time", "2019-01-02 11:11:22");
    item.put("pageviews", 1485);
    item.put("status", "draft");
    item.put("title", "복슬이 바보");
    result.add(item);

    item = new HashMap<>();
    item.put("author", "복슬이");
    item.put("id", "121212");
    item.put("display_time", "2019-01-02 11:11:22");
    item.put("pageviews", 1485);
    item.put("status", "draft");
    item.put("title", "복슬이 바보");
    result.add(item);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}

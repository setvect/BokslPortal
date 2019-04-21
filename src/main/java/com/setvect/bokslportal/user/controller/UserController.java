package com.setvect.bokslportal.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.setvect.bokslportal.user.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 사용자
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {

  // ============== 조회 ==============

  /**
   * 로그인 사용자 정보
   *
   * @param token 토큰
   * @return 사용자 정보
   */
  @RequestMapping(value = "/info.json", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<UserVo> info(@RequestParam("token") final String token) {
    UserVo userInfo = new UserVo();
    userInfo.setUserId(token);
    userInfo.setName("복슬이");
    userInfo.addRole("admin");
    return new ResponseEntity<>(userInfo, HttpStatus.OK);
  }


  /**
   * @param username 아이디
   * @param password 비번
   * @return 토큰
   */
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<Map<String, Object>> login(@RequestParam("username") final String username,
                                                   @RequestParam("password") final String password) {
    Map<String, Object> result = new HashMap<>();
    result.put("token", username);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  // ============== 등록 ==============

  // ============== 수정 ==============

  // ============== 삭제 ==============
}

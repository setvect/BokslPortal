package com.setvect.bokslportal.user.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.user.repository.UserRepository;
import com.setvect.bokslportal.user.service.UserService;
import com.setvect.bokslportal.user.vo.UserVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * 사용자
 */
@RestController
@RequestMapping(value = "/user/")
@Log4j2
public class UserController {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserService userService;

  // ============== 조회 ==============

  /**
   * 로그인 사용자 정보
   *
   * @param token 토큰
   * @return 사용자 정보
   */
  @RequestMapping(value = "/info.json", method = RequestMethod.GET)
  public ResponseEntity<UserVo> info(@RequestParam("token") final String token) {
    UserVo user = ApplicationUtil.getLoginUser();

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  /**
   * @param authenticationRequest 로그인 정보
   * @param session               새션
   * @return 로그인 결과
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public AuthenticationToken login(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session) {
    String username = authenticationRequest.getUsername();
    String password = authenticationRequest.getPassword();

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
      SecurityContextHolder.getContext());

    UserVo user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    return new AuthenticationToken(user.getUserId(), user.getName(), user.getAuthorities(), session.getId());
  }

  // ============== 등록 ==============

  // ============== 수정 ==============

  // ============== 삭제 ==============


}

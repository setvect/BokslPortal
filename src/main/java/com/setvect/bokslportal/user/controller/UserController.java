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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
  @Autowired
  private PasswordEncoder passwordEncoder;

  // ============== 조회 ==============

  /**
   * 로그인 사용자 정보<br>
   * 토큰 정보를 이용해 사용자를 찾음
   *
   * @return 사용자 정보
   */
  @GetMapping("/info")
  public ResponseEntity<UserVo> info() {
    UserVo user = ApplicationUtil.getLoginUser();
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  /**
   * @param authenticationRequest 로그인 정보
   * @param session               새션
   * @return 로그인 결과
   */
  @PostMapping("/login")
  public AuthenticationToken login(AuthenticationRequest authenticationRequest, HttpSession session) {
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


  /**
   * @return 로그 아웃
   */
  @PostMapping("/logout")
  public ResponseEntity<Object> logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    SecurityContextHolder.clearContext();
    if (session != null) {
      session.invalidate();
    }

    return ResponseEntity.ok().build();
  }

  // ============== 등록 ==============

  // ============== 수정 ==============

  /**
   * 패스워드 변경
   *
   * @param currentPassword 현재 비밀번호
   * @param changePassword  변경할 비밀번호
   */
  @PatchMapping(value = "/user-change-password")
  public ResponseEntity<Void> edit(@RequestParam("currentPassword") final String currentPassword, @RequestParam("changePassword") final String changePassword) {
    UserVo login = ApplicationUtil.getLoginUser();
    UserVo user = userRepository.findById(login.getUserId()).get();

    boolean match = passwordEncoder.matches(currentPassword, user.getPassword());
    if (!match) {
      throw new RuntimeException("Passwords do not match.");
    }
    user.setPassword(passwordEncoder.encode(changePassword));

    userRepository.save(user);
    return ResponseEntity.noContent().build();
  }

  // ============== 삭제 ==============


}

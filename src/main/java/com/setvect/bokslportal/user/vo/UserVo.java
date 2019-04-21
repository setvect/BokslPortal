package com.setvect.bokslportal.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 정보
 */
@Getter
@Setter
@ToString
public class UserVo {
  /**
   * 이름
   */
  private String name;
  /**
   * 아이디
   */
  private String userId;
  /**
   * 권한
   */
  private List<String> roles = new ArrayList<>();

  /**
   * 권한 추가
   *
   * @param role
   */
  public void addRole(final String role) {
    roles.add(role);
  }
}

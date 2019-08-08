package com.setvect.bokslportal.todo.service;

import java.util.Set;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TodoSearch extends SearchListVo {
  /**
   * 내용 검색
   */
  private String searchContent;
  /**
   * 비활성화 포함
   */
  private boolean searchDisable;

  /**
   * 상태
   */
  private Set<Status> searchStatus;
}

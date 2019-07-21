package com.setvect.bokslportal.todo.service;

import com.setvect.bokslportal.common.SearchListVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class TodoSearch extends SearchListVo {

  public TodoSearch(int startCursor, int returnCount) {
    super(startCursor, returnCount);
  }

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

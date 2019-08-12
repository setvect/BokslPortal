package com.setvect.bokslportal.todo.service;

import java.util.HashSet;
import java.util.Set;

import com.setvect.bokslportal.common.SearchListVo;
import com.setvect.bokslportal.todo.vo.TodoVo.StatusType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoSearch extends SearchListVo {
  /**
   * 내용 검색
   */
  private String word;

  /**
   * 상태
   */
  private Set<StatusType> statusType = new HashSet<>();
}

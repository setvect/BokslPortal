package com.setvect.bokslportal.todo.service;

import java.util.Date;
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
  private String word;
  /**
   * 비활성화 포함
   */
  private boolean disable;

  /** 기준 검색일 */
  private Date baseDate;

  /**
   * 상태
   */
  private Set<Status> status;
}

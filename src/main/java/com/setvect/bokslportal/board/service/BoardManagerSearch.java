package com.setvect.bokslportal.board.service;

import com.setvect.bokslportal.common.SearchListVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
@Getter
@Setter
public class BoardManagerSearch extends SearchListVo {
  public enum Field {
    name,
    code,
  }

  private Field field;
  private String word;
}

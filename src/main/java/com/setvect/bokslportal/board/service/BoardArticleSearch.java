package com.setvect.bokslportal.board.service;

import com.setvect.bokslportal.common.SearchListVo;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
@Getter
@Setter
public class BoardArticleSearch extends SearchListVo {
  public enum Field {
    title,
    content,
  }

  private Field field;
  private String word;

  private String boardCode;
}

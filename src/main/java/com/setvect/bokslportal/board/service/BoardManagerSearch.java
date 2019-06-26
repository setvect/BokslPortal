package com.setvect.bokslportal.board.service;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
@Getter
@Setter
public class BoardManagerSearch extends SearchListVo {

  private String searchName;
  private String searchCode;

  public BoardManagerSearch(int startCursor, int returnCount) {
    super(startCursor, returnCount);
  }

  /**
   * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
   */
  public String getWord() {
    if (!StringUtils.isEmpty(searchCode)) {
      return searchCode;
    }
    if (!StringUtils.isEmpty(searchName)) {
      return searchName;
    }
    return null;
  }

}

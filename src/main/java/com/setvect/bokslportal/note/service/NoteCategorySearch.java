package com.setvect.bokslportal.note.service;

import com.setvect.bokslportal.common.SearchListVo;

/**
 * 노트 카테고리 페이지 검색 조건
 */
public class NoteCategorySearch extends SearchListVo {

  public NoteCategorySearch(int startCursor, int returnCount) {
    super(startCursor, returnCount);
  }

}

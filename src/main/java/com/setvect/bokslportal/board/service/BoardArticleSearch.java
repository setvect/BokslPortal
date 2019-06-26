package com.setvect.bokslportal.board.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시물 관리 목록 페이징 및 검색 조건
 */
@Getter
@Setter
public class BoardArticleSearch extends SearchListVo {

  private String searchName;
  private String searchCode;
  /**
   * 복수의 게시물 조회<br>
   * 본 필드에 값이 있으면 searchCode 값은 무시함
   */
  private List<String> searchCodes;
  private String searchTitle;
  private String searchContent;

  /**
   * 삭제된 게시물도 보여 줄 것이지
   */
  private boolean deleteView;

  public BoardArticleSearch(int startCursor, int returnCount) {
    super(startCursor, returnCount);
  }

  /**
   * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
   */
  public String getWord() {

    if (!StringUtils.isEmpty(searchName)) {
      return searchName;
    }

    if (!StringUtils.isEmpty(searchTitle)) {
      return searchTitle;
    }

    if (!StringUtils.isEmpty(searchContent)) {
      return searchContent;
    }
    return null;
  }

}

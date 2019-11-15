package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.board.service.BoardArticleSearch;
import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.common.GenericPage;

/**
 * 게시물
 */
public interface BoardArticleRepositoryCustom {

  /**
   * @param pageCondition 게시물 검색 정보
   * @return 게시물 페이지 값
   */
  GenericPage<BoardArticleVo> getPagingList(BoardArticleSearch pageCondition);

}

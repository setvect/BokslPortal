package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.board.service.BoardManagerSearch;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.common.GenericPage;

/**
 * 게시판 관리
 */
public interface BoardManagerRepositoryCustom {
  /**
   * @param pageCondition
   * @return 게시판생성 정보 항목
   */
  GenericPage<BoardManagerVo> getPagingList(BoardManagerSearch pageCondition);

}

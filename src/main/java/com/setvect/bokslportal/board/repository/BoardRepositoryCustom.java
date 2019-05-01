package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.board.service.BoardManagerSearch;
import com.setvect.bokslportal.board.vo.BoardVo;
import com.setvect.bokslportal.common.GenericPage;

/**
 * 게시판 관리
 */
public interface BoardRepositoryCustom {
	/**
	 * @param pageCondition
	 * @return 게시판생성 정보 항목
	 */
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition);

}

package com.setvect.bokslportal.comment.repository;

import com.setvect.bokslportal.comment.service.CommentSearch;
import com.setvect.bokslportal.comment.vo.CommentVo;
import com.setvect.bokslportal.common.GenericPage;

/**
 * 코멘트
 */
public interface CommentRepositoryCustom {

	/**
	 * @param pageCondition
	 *            가져올 갯수
	 * @return 코멘트 목록
	 */
	public GenericPage<CommentVo> getCommentPagingList(CommentSearch pageCondition);

}

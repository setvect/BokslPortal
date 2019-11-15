package com.setvect.bokslportal.comment.repository;

import com.setvect.bokslportal.comment.service.CommentSearch;
import com.setvect.bokslportal.comment.vo.CommentVo;
import com.setvect.bokslportal.common.GenericPage;

public interface CommentRepositoryCustom {
  /**
   * @param pageCondition
   * @return 코맨트
   */
  GenericPage<CommentVo> getPagingList(CommentSearch pageCondition);

}

package com.setvect.bokslportal.knowledge.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.knowledge.service.KnowledgeSearch;
import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;

public interface KnowledgeRepositoryCustom {

  /**
   * @param pageCondition 게시물 검색 정보
   * @return 게시물 페이지 값
   */
  GenericPage<KnowledgeVo> getPagingList(KnowledgeSearch pageCondition);
}

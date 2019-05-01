package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteCategorySearch;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;

/**
 * 노트 카테고리
 */
public interface NoteCategoryRepositoryCustom {

	/**
	 * @param pageCondition
	 * @return 게시판생성 정보 항목
	 */
	public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch pageCondition);

}

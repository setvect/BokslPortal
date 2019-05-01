package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.vo.NoteVo;

/**
 * 노트 내용
 */
public interface NoteRepositoryCustom {

	/**
	 * @param pageCondition
	 *            노트 검색 정보
	 * @return 노트 목록
	 */
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition);

}

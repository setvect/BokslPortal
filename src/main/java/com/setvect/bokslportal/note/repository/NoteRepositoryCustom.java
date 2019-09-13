package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.vo.NoteVo;

/**
 * 노트 내용
 */
public interface NoteRepositoryCustom {
  /**
   *
   * @param searchCondition 검색 조건
   * @return 페이징 정보
   */
  public GenericPage<NoteVo> getPagingList(NoteSearch searchCondition);
}

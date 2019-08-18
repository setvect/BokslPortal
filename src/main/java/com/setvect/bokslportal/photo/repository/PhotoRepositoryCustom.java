package com.setvect.bokslportal.photo.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.photo.service.PhotoSearch;
import com.setvect.bokslportal.photo.vo.PhotoVo;

/**
 * 사진 검색 조건
 */
public interface PhotoRepositoryCustom {

  /**
   * @param pageCondition
   *          조회 조건
   * @return 사진 목록
   */
  public GenericPage<PhotoVo> getPagingList(PhotoSearch pageCondition);

}

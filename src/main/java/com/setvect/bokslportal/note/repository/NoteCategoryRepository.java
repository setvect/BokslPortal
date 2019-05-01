package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 노트 카테고리
 */
public interface NoteCategoryRepository extends JpaRepository<NoteCategoryVo, Integer>, NoteCategoryRepositoryCustom {

}

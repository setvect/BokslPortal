package com.setvect.bokslportal.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 노트 카테고리
 */
public interface NoteCategoryRepository extends JpaRepository<NoteCategoryVo, Integer>{
  @Query("select category from NoteCategoryVo category where category.deleteF = false ")
  List<NoteCategoryVo> listCategory();

}

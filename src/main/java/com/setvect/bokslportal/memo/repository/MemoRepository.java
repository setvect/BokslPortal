package com.setvect.bokslportal.memo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.setvect.bokslportal.memo.vo.MemoVo;

/**
 * 메모장 Repository
 */
public interface MemoRepository extends JpaRepository<MemoVo, Integer>, MemoRepositoryCustom {
  @Query("select memo from MemoVo memo where memo.category.categorySeq =?1 and memo.deleteF = 'N' order by memo.uptDate desc")
  List<MemoVo> findByCategorySeq(int categorySeq);
}

package com.setvect.bokslportal.ctmemo.repository;

import com.setvect.bokslportal.ctmemo.vo.CtmemoVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 메모장 Repository
 */
public interface CtmemoRepository extends JpaRepository<CtmemoVo, Integer>, CtmemoRepositoryCustom {
  @Query("select c from CtmemoVo c where workspaceSeq =?1 and deleteF = 'N' order by c.uptDate desc")
  List<CtmemoVo> findByWorkspaceSeq(int workspaceSeq);
}

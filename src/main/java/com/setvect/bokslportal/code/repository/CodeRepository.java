package com.setvect.bokslportal.code.repository;

import com.setvect.bokslportal.code.vo.CodeVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 코드관리
 */
public interface CodeRepository extends JpaRepository<CodeVo, Integer> {

  List<CodeVo> findByMajorCodeOrderByOrderNoAsc(String majorCode);

}

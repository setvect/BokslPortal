package com.setvect.bokslportal.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.code.vo.CodeVo;


/**
 * 코드관리
 */
public interface CodeRepository extends JpaRepository<CodeVo, Integer> {

	public List<CodeVo> findByMajorCodeOrderByOrderNoAsc(String majorCode);

}

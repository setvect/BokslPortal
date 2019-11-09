package com.setvect.bokslportal.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.memo.vo.MemoCategoryVo;

/**
 * 메모장 Repository
 */
public interface MemoCategoryRepository extends JpaRepository<MemoCategoryVo, Integer> {

}

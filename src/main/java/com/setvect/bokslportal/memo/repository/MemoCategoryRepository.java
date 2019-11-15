package com.setvect.bokslportal.memo.repository;

import com.setvect.bokslportal.memo.vo.MemoCategoryVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 메모장 Repository
 */
public interface MemoCategoryRepository extends JpaRepository<MemoCategoryVo, Integer> {

}

package com.setvect.bokslportal.ctmemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.ctmemo.vo.WorkspaceVo;

/**
 * 메모장 Repository
 */
public interface WorkspaceRepository extends JpaRepository<WorkspaceVo, Integer> {

}

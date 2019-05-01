package com.setvect.bokslportal.ctmemo.repository;

import com.setvect.bokslportal.ctmemo.vo.WorkspaceVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 메모장 Repository
 */
public interface WorkspaceRepository extends JpaRepository<WorkspaceVo, Integer> {

}

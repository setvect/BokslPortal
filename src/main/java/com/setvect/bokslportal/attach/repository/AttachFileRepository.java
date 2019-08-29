package com.setvect.bokslportal.attach.repository;

import com.setvect.bokslportal.attach.service.AttachFileModule;
import com.setvect.bokslportal.attach.vo.AttachFileVo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 첨부파일
 */
public interface AttachFileRepository extends JpaRepository<AttachFileVo, Integer> {

  public List<AttachFileVo> findByModuleNameAndModuleId(AttachFileModule moduleName, String moduleId);

}

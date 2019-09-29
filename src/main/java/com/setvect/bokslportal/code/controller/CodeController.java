package com.setvect.bokslportal.code.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.code.repository.CodeRepository;
import com.setvect.bokslportal.code.service.CodeService;
import com.setvect.bokslportal.code.vo.CodeVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/code/")
@Log4j2
public class CodeController {
  @Autowired
  private CodeRepository codeRepository;
  @Autowired
  private CodeService codeService;


  // ============== 조회 ==============

  /**
   * @param majorCode 메인코드
   * @return 메인코드 밑에 하위 코드 목록
   */
  @GetMapping("list/{majorCode}")
  public ResponseEntity<String> getCategoryPath(@PathVariable("majorCode") String majorCode) {
    List<CodeVo> codeList = codeRepository.findByMajorCodeOrderByOrderNoAsc(majorCode);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(codeList));
  }

  // ============== 등록 ==============

  // ============== 수정 ==============


  // ============== 삭제 ==============
}

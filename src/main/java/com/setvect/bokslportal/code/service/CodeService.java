package com.setvect.bokslportal.code.service;

import com.setvect.bokslportal.code.repository.CodeRepository;
import com.setvect.bokslportal.code.vo.CodeVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CodeService {
  @Autowired
  private CodeRepository codeRepository;

  /**
   * 기본 코드 정보 만듦.
   */
  public void init() {
    List<CodeVo> codeList = codeRepository.findAll();
    if (codeList.size() != 0) {
      return;
    }
    List<CodeVo> allCategory = new ArrayList<>();

    CodeVo root = new CodeVo();
    root.setMajorCode(CodeType.ROOT.name());
    root.setMinorCode(CodeType.KNOW_TYPE.name());
    root.setCodeValue("ROOT");
    root.setOrderNo(1);
    allCategory.add(root);

    AtomicInteger seq = new AtomicInteger(1);
    Set<CodeVo> codes = Arrays.asList("JAVASCRIPT", "JAVA", "DBMS", "OS", "ETC").stream().map(c -> {
      CodeVo code = new CodeVo();
      code.setMajorCode(CodeType.KNOW_TYPE.name());
      code.setMinorCode(c);
      code.setCodeValue(c);
      code.setOrderNo(seq.getAndIncrement());
      return code;
    }).collect(Collectors.toSet());

    allCategory.addAll(codes);

    codeRepository.saveAll(allCategory);
  }

}

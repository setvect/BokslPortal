package com.setvect.bokslportal.memo.service;

import com.setvect.bokslportal.memo.repository.MemoRepository;
import com.setvect.bokslportal.memo.repository.MemoCategoryRepository;
import com.setvect.bokslportal.memo.vo.MemoCategoryVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class MemoService {
  @Autowired
  private MemoCategoryRepository categoryRepository;

  @Autowired
  private MemoRepository memoRepository;

  /**
   * 기본 분류 정보를 넣는다.
   */
  public void init() {
    List<MemoCategoryVo> categoryList = categoryRepository.findAll();
    if (categoryList.size() != 0) {
      return;
    }
    Arrays.asList("잡동사니", "일", "생각").forEach(name -> {
      MemoCategoryVo category = new MemoCategoryVo();
      category.setName(name);
      categoryRepository.save(category);
    });
  }
}

package com.setvect.bokslportal.memo.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.memo.repository.CategoryRepository;
import com.setvect.bokslportal.memo.repository.MemoRepository;
import com.setvect.bokslportal.memo.service.MemoConstrant;
import com.setvect.bokslportal.memo.vo.MemoCategoryVo;
import com.setvect.bokslportal.memo.vo.MemoVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/memo/")
@Log4j2
public class MemoController {
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private MemoRepository memoRepository;

  // ============== 조회 ==============

  /**
   * @param categorySeq 카테고리 일련번호
   * @return 메모 목록
   */
  @GetMapping("list/{categorySeq}")
  public ResponseEntity<String> listMemo(@PathVariable("categorySeq") int categorySeq) {
    List<MemoVo> list = memoRepository.findByCategorySeq(categorySeq);
    String json = ApplicationUtil.toJson(list, "-category");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @return 카테고리 목록
   */
  @GetMapping("category/list")
  public ResponseEntity<List<MemoCategoryVo>> listMemo() {
    List<MemoCategoryVo> categoryList = categoryRepository.findAll();
    return ResponseEntity.ok().body(categoryList);
  }


  /**
   * @param memoSeq 일련번호
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int memoSeq) {
    Optional<MemoVo> item = memoRepository.findById(memoSeq);
    String body = ApplicationUtil.toJson(item.get(), "**,category[-handler,-hibernateLazyInitializer]");
    return ResponseEntity.ok().body(body);
  }

  // ------- 등록

  /**
   * @param memo 메모
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<String> addMemo(MemoVo memo, @RequestParam("categorySeq") int categorySeq) {
    memo.setRegDate(new Date());
    memo.setUptDate(memo.getRegDate());
    if (StringUtils.isEmpty(memo.getBgCss())) {
      memo.setBgCss(MemoConstrant.BGSTYLE_1);
    }
    if (StringUtils.isEmpty(memo.getFontCss())) {
      memo.setFontCss(MemoConstrant.FONTSTYLE_1);
    }
    return saveMemo(memo, categorySeq);
  }
// ------- 수정

  /**
   * @param memo 메모
   * @return 할일 정보
   */
  @PutMapping("item")
  public ResponseEntity<String> editMemo(MemoVo memo, @RequestParam("categorySeq") int categorySeq) {
    memo.setUptDate(new Date());

    return saveMemo(memo, categorySeq);
  }

// ------- 삭제

  /**
   * @param memoSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteMemo(@PathVariable("id") int memoSeq) {
    MemoVo saveData = memoRepository.getOne(memoSeq);
    saveData.setDeleteF(true);
    memoRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }

  // ------- 기타
  private ResponseEntity<String> saveMemo(MemoVo memo, int categorySeq) {
    Optional<MemoCategoryVo> category = categoryRepository.findById(categorySeq);
    memo.setCategory(category.get());

    memoRepository.save(memo);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(memo));
  }

}

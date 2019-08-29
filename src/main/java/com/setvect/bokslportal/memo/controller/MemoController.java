package com.setvect.bokslportal.memo.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.memo.repository.MemoRepository;
import com.setvect.bokslportal.memo.repository.CategoryRepository;
import com.setvect.bokslportal.memo.vo.MemoCategoryVo;
import com.setvect.bokslportal.memo.vo.MemoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
  @GetMapping("memo/list/{categorySeq}")
  public ResponseEntity<String> listMemo(@PathVariable("categorySeq") int categorySeq) {
    List<MemoVo> list = memoRepository.findByCategorySeq(categorySeq);
    String json = ApplicationUtil.toJson(list, "-category");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @return 카테고리 목록
   */
  @GetMapping("memo/workspace/list")
  public ResponseEntity<List<MemoCategoryVo>> listMemo() {
    List<MemoCategoryVo> categoryList = categoryRepository.findAll();
    return ResponseEntity.ok().body(categoryList);
  }

  // ------- 등록

  /**
   * @param memo 메모
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<String> addMemo(MemoVo memo) {
    memo.setRegDate(new Date());
    memo.setUptDate(memo.getRegDate());
    memoRepository.save(memo);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(memo));
  }
// ------- 수정

  /**
   * @param memo 메모
   * @return 할일 정보
   */
  @PutMapping("item")
  public ResponseEntity<String> editMemo(MemoVo memo) {
    memo.setUptDate(new Date());
    memoRepository.save(memo);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(memo));
  }
// ------- 삭제

  /**
   * @param memoSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "memo/{id}")
  public ResponseEntity<Void> deleteMemo(@PathVariable("id") int memoSeq) {
    MemoVo saveData = memoRepository.getOne(memoSeq);
    saveData.setDeleteF(true);
    memoRepository.save(saveData);
    return ResponseEntity.noContent().build();
  }
}

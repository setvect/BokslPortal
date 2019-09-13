package com.setvect.bokslportal.note.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.note.repository.NoteCategoryRepository;
import com.setvect.bokslportal.note.repository.NoteRepository;
import com.setvect.bokslportal.note.service.NoteService;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import com.setvect.bokslportal.util.TreeNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/note/")
@Log4j2
public class NoteController {
  @Autowired
  private NoteCategoryRepository categoryRepository;
  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private NoteService noteService;


  // ============== 조회 ==============

  /**
   * @return 카테고리 정보를 상하위 관계로 된 트리 형태로
   */
  @GetMapping("tree")
  public ResponseEntity<String> getTree() {
    TreeNode<NoteCategoryVo> tree = noteService.getCategoryTree();
    // ROOT를 제외하고 5 Depth까지 하여 하위 카테고리 반환
    // 이 방법이 최선이다. ㅡㅡ;
    return ResponseEntity.ok().body(ApplicationUtil.toJson(tree, "data[-parent,-children],children[data[-parent,-children],children[data[-parent,-children],children[data[-parent,-children],children[data[-parent,-children]]]]]"));
  }

  /**
   * @param categorySeq 기준 카테고리
   * @return ROOT부터 기준 카테고리 까지 경로 제공
   */
  @GetMapping("category-path/{id}")
  public ResponseEntity<String> getCategoryPath(@PathVariable("id") int categorySeq) {
    List<NoteCategoryVo> categoryPath = noteService.geCategoryPath(categorySeq);
    return ResponseEntity.ok().body(ApplicationUtil.toJson(categoryPath, "**,-parent,-children"));
  }


  // ============== 등록 ==============


  // ============== 수정 ==============

  /**
   * @param category 할일
   * @return
   */
  @PutMapping("category")
  public ResponseEntity<Void> applyCategory(@RequestBody NoteCategoryVo category) {
    noteService.updateCategory(category);
    return ResponseEntity.noContent().build();
  }

  // ============== 삭제 ==============
}

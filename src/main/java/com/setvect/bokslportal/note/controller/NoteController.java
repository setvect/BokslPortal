package com.setvect.bokslportal.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.context.provider.AbstractSquigglyContextProvider;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

  // ============== 등록 ==============


  // ============== 수정 ==============


  // ============== 삭제 ==============
}

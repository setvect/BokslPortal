package com.setvect.bokslportal.comment.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.comment.repository.CommentRepository;
import com.setvect.bokslportal.comment.service.CommentSearch;
import com.setvect.bokslportal.comment.vo.CommentVo;
import com.setvect.bokslportal.common.GenericPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 코멘트
 */
@RestController
@RequestMapping(value = "/comment/")
public class CommentController {
  @Autowired
  private CommentRepository commentRepository;

  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 코멘트 목록
   */
  @GetMapping("page")
  public ResponseEntity<String> page(CommentSearch param) {
    GenericPage<CommentVo> page = commentRepository.getPagingList(param);
    String json = ApplicationUtil.toJson(page, "**,list[user[userId,name],**]");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param commentSeq 일련번호
   * @return 코멘트 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<String> getItem(@PathVariable("id") int commentSeq) {
    CommentVo item = commentRepository.getOne(commentSeq);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(item, "**,user[userId,name]"));
  }

  // ------- 등록

  /**
   * @param comment 코멘트
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<String> addItem(CommentVo comment) {
    comment.setRegDate(new Date());
    comment.setUser(ApplicationUtil.getLoginUser());
    commentRepository.save(comment);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(comment, "**,user[userId,name]"));
  }

  // ------- 수정

  /**
   * @param comment 일일
   * @return 코멘트 정보
   */
  @PutMapping("item")
  public ResponseEntity<String> editItem(CommentVo comment) {
    commentRepository.save(comment);
    return ResponseEntity.ok().body(ApplicationUtil.toJsonWtihRemoveHibernate(comment, "**,user[userId,name]"));
  }

  // ------- 삭제

  /**
   * @param commentSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Void> deleteItem(@PathVariable("id") int commentSeq) {
    commentRepository.deleteById(commentSeq);
    return ResponseEntity.noContent().build();
  }
}

package com.setvect.bokslportal.todo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.repository.TodoRepository;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoVo;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/todo/")
@Log4j2
public class TodoController {
  @Autowired
  private TodoRepository todoRepository;

  // ============== 조회 ==============

  /**
   * @param param
   *          검색 조건
   * @return 할일 목록
   */
  @GetMapping("list")
  public ResponseEntity<String> listTodo(TodoSearch param) {
    GenericPage<TodoVo> page = todoRepository.getTodoPagingList(param);
    String json = ApplicationUtil.toJson(page, "**,list[**]");
    return ResponseEntity.ok().body(json);
  }

  /**
   * @param todoSeq
   *          일련번호
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public ResponseEntity<TodoVo> getTodo(@PathVariable("id") int todoSeq) {
    TodoVo item = todoRepository.getOne(todoSeq);
    return ResponseEntity.ok().body(item);
  }

  // ------- 등록

  /**
   * @param todo
   *          할일
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<TodoVo> addTodo(TodoVo todo) {
    todo.setRegDate(new Date());
    todo.setStatusType(TodoVo.StatusType.PLAN);
    todoRepository.save(todo);
    return ResponseEntity.ok().body(todo);
  }

  /**
   * @param todoSeq
   *          일련번호
   * @param checkType
   *          유형
   * @return 할일 정보
   */
  @PatchMapping("check")
  public ResponseEntity<TodoVo> addCheck(@RequestParam("todoSeq") int todoSeq,
      @RequestParam("checkType") TodoVo.StatusType checkType) {
    TodoVo todo = todoRepository.getOne(todoSeq);
    todo.setCheckDate(new Date());
    todo.setStatusType(checkType);
    todoRepository.save(todo);
    return ResponseEntity.ok().body(todo);
  }

  // ------- 수정

  /**
   * @param todo
   *          일일
   * @return 할일 정보
   */
  @PutMapping("item")
  public ResponseEntity<TodoVo> editTodo(TodoVo todo) {
    todoRepository.save(todo);
    return ResponseEntity.ok().body(todo);
  }

  // ------- 삭제

  /**
   * @param todoSeq
   *          일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity deleteTodo(@PathVariable("id") int todoSeq) {
    todoRepository.deleteById(todoSeq);
    return ResponseEntity.noContent().build();
  }
}

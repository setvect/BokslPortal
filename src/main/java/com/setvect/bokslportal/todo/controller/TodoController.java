package com.setvect.bokslportal.todo.controller;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.repository.TodoCheckRepository;
import com.setvect.bokslportal.todo.repository.TodoRepository;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoCheckVo;
import com.setvect.bokslportal.todo.vo.TodoVo;
import com.setvect.bokslportal.todo.vo.TodoVo.Period;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(value = "/todo/")
@Log4j2
public class TodoController {
  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TodoCheckRepository todoCheckRepository;

  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 할일 목록
   */
  @GetMapping("list")
  public ResponseEntity<String> listTodo(TodoSearch param) {
    GenericPage<TodoVo> page = todoRepository.getTodoPagingList(param);
    page.getList().stream().forEach(todo -> todo.setStatus(TodoVo.Status.PLAN));
    String json = ApplicationUtil.toJson(page, "**,list[**,-todoCheckList]");
    return new ResponseEntity<>(json, HttpStatus.OK);
  }

  /**
   * @param todoSeq 일련번호
   * @return 할일 목록
   */
  @GetMapping("item/{id}")
  public TodoVo getTodo(@PathVariable("id") int todoSeq) {
    TodoVo item = todoRepository.getOne(todoSeq);
    return item;
  }

  // ------- 등록

  /**
   * @param todo 할일
   * @return 등록된 항목 일련번호
   */
  @PostMapping("item")
  public ResponseEntity<Integer> addTodo(TodoVo todo) {
    todo.setRegDate(new Date());
    if (todo.getPeriod() == Period.ONCE) {
      todo.setDurationFrom(null);
      todo.setDurationTo(null);
    }
    todoRepository.save(todo);
    return new ResponseEntity<>(todo.getTodoSeq(), HttpStatus.OK);
  }

  /**
   * 할일 체크
   *
   * @param check   체크 정보
   * @return 할일 체크 일련번호
   */
  @PostMapping("check")
  public ResponseEntity<Integer> addCheck(TodoCheckVo check) {
    todoCheckRepository.save(check);
    return new ResponseEntity<>(check.getTodoCheckSeq(), HttpStatus.OK);
  }

  // ------- 수정

  /**
   * @param todo 일일
   * @return 성공여부
   */
  @PutMapping("item")
  public ResponseEntity<Boolean> editTodo(TodoVo todo) {
    todoRepository.save(todo);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  // ------- 삭제

  /**
   * @param todoSeq 일련번호
   * @return 성공여부
   */
  @DeleteMapping(value = "item/{id}")
  public ResponseEntity<Boolean> deleteTodo(@PathVariable("id") int todoSeq) {
    todoRepository.deleteById(todoSeq);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }
}

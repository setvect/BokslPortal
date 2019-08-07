package com.setvect.bokslportal.todo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.repository.TodoCheckRepository;
import com.setvect.bokslportal.todo.repository.TodoRepository;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoCheckVo;
import com.setvect.bokslportal.todo.vo.TodoVo;
import com.setvect.bokslportal.todo.vo.TodoVo.Period;

import lombok.extern.log4j.Log4j2;

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
   * @param param
   *          검색 조건
   * @return 할일 목록
   */
  @RequestMapping(value = "/listTodo.do", method = RequestMethod.GET)
  public List<TodoVo> listTodo(TodoSearch param) {
    GenericPage<TodoVo> list = todoRepository.getTodoPagingList(param);
    return list.getObjects();
  }

  /**
   * @param param
   *          검색 조건
   * @return 할일 목록
   */
  @RequestMapping(value = "/getTodo.do", method = RequestMethod.GET)
  public TodoVo getTodo(@RequestParam("todoSeq") int todoSeq) {
    TodoVo item = todoRepository.getOne(todoSeq);
    return item;
  }

  // ------- 등록
  /**
   * @param todo
   *          할일
   * @return 등록된 항목 일련번호
   */
  @RequestMapping(value = "/addTodo.do", method = RequestMethod.POST)
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
   * @param todoSeq
   *          할일 일련번호
   * @param check
   *          체크 정보
   * @return 할일 체크 일련번호
   */
  public ResponseEntity<Integer> addCheck(@RequestParam("todoSeq") int todoSeq, TodoCheckVo check) {
    check.setTodo(todoRepository.getOne(todoSeq));
    todoCheckRepository.save(check);
    return new ResponseEntity<>(check.getTodoCheckSeq(), HttpStatus.OK);
  }

  // ------- 수정
  /**
   * @param todo
   *          일일
   * @return 성공여부
   */
  @RequestMapping(value = "/edtiTodo.do", method = RequestMethod.PUT)
  public ResponseEntity<Boolean> editTodo(TodoVo todo) {
    todoRepository.save(todo);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  // ------- 삭제
  /**
   * @param todoSeq
   *          일련번호
   * @return 성공여부
   */
  @RequestMapping(value = "/deleteTodo.do", method = RequestMethod.DELETE)
  public ResponseEntity<Boolean> deleteTodo(@RequestParam("todoSeq") int todoSeq) {
    todoRepository.deleteById(todoSeq);
    return new ResponseEntity<>(true, HttpStatus.OK);
  }
}

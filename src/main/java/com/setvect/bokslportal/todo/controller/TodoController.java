package com.setvect.bokslportal.todo.controller;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.repository.TodoRepository;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/todo/")
@Log4j2
public class TodoController {
  @Autowired
  private TodoRepository todoRepository;

  // ============== 조회 ==============

  /**
   * @param param 검색 조건
   * @return 할일 목록
   */
  public List<TodoVo> list(TodoSearch param) {
    GenericPage<TodoVo> list = todoRepository.getTodoPagingList(param);
    return list.getObjects();
  }

  // ============== 등록 ==============

  // ============== 수정 ==============

  // ============== 삭제 ==============

}

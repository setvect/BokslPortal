package com.setvect.bokslportal.todo.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoVo;

public interface TodoRepositoryCustom {
  /**
   * @param pageCondition
   * @return TODO 정보 항목
   */
  GenericPage<TodoVo> getPagingList(TodoSearch pageCondition);

}

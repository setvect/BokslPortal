package com.setvect.bokslportal.todo.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoVo;

public class TodoRepositoryImpl implements TodoRepositoryCustom
{
  @Override
  public GenericPage<TodoVo> getTodoPagingList(TodoSearch pageCondition) {
    return null;
  }
}

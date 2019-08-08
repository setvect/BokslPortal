package com.setvect.bokslportal.todo.repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

public class TodoRepositoryImpl implements TodoRepositoryCustom {
  /** JPA DB 세션 */
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<TodoVo> getTodoPagingList(TodoSearch searchCondition) {
    Map<String, Object> bindParameter = new HashMap<>();
    String where = " WHERE p.deleteF = 'N' ";

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);

    pageQuery.setCountQuery("select count(*) FROM TodoVo p " + where);
    pageQuery.setSelectQuery("SELECT p FROM TodoVo p " + where + " ORDER BY p.regDate DESC");

    GenericPage<TodoVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, TodoVo.class);
    return resultPage;
  }
}

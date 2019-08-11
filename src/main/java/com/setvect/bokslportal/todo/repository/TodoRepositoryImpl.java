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
import org.apache.commons.lang3.StringUtils;

public class TodoRepositoryImpl implements TodoRepositoryCustom {
  /**
   * JPA DB 세션
   */
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<TodoVo> getTodoPagingList(TodoSearch searchCondition) {
    Map<String, Object> bindParameter = new HashMap<>();
    StringBuffer selectQuery = new StringBuffer("select todo FROM TodoVo todo");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM TodoVo todo");

    StringBuffer where = new StringBuffer(" WHERE todo.deleteF = 'N'");
    if (StringUtils.isNotBlank(searchCondition.getWord())) {
      where.append(" AND todo.content like :word");
      bindParameter.put("word", "%" + searchCondition.getWord() + "%");
    }

    countQuery.append(where);
    selectQuery.append(where + " ORDER BY todo.regDate DESC");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<TodoVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, TodoVo.class);
    return resultPage;
  }
}

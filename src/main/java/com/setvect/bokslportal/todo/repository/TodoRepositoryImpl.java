package com.setvect.bokslportal.todo.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.todo.service.Status;
import com.setvect.bokslportal.todo.service.TodoSearch;
import com.setvect.bokslportal.todo.vo.TodoCheckVo;
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
    StringBuffer selectQuery = new StringBuffer("select todo FROM TodoVo as todo left outer join todo.todoCheckList as check ");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM TodoVo as todo left outer join todo.todoCheckList as check");

    StringBuffer where = new StringBuffer(" WHERE todo.deleteF = 'N'");
    if (StringUtils.isNotBlank(searchCondition.getWord())) {
      where.append(" AND todo.content like :word");
      bindParameter.put("word", "%" + searchCondition.getWord() + "%");
    }

    Set<Status> status = searchCondition.getStatus();
    if (!status.isEmpty()) {
      where.append(" AND (1 = 0");
      if (status.contains(Status.plan)) {
        where.append(" OR check is null");
      }
      if (status.contains(Status.complete)) {
        where.append(" OR check.checkType = :checkType_c");
        bindParameter.put("checkType_c", TodoCheckVo.CheckType.DO);
      }
      if (status.contains(Status.giveup)) {
        where.append(" OR check.checkType = :checkType_c");
        bindParameter.put("checkType_c", TodoCheckVo.CheckType.NOT_DO);
      }
      where.append(" )");
    }

    selectQuery.append(where);
    countQuery.append(where + " ORDER BY todo.regDate DESC");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(selectQuery.toString());
    pageQuery.setSelectQuery(countQuery.toString());

    GenericPage<TodoVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, TodoVo.class);
    return resultPage;
  }
}

package com.setvect.bokslportal.comment.repository;

import com.setvect.bokslportal.comment.service.CommentSearch;
import com.setvect.bokslportal.comment.vo.CommentVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class CommentRepositoryImpl implements CommentRepositoryCustom {
  /**
   * JPA DB 세션
   */
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<CommentVo> getPagingList(CommentSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select comment FROM CommentVo comment");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM CommentVo comment");

    StringBuffer where = new StringBuffer(" WHERE ");
    Map<String, Object> bindParameter = new HashMap<>();

    where.append(" comment.moduleName = :moduleName");
    bindParameter.put("moduleName", searchCondition.getModuleName());

    where.append(" AND comment.moduleId = :moduleId");
    bindParameter.put("moduleId", searchCondition.getModuleId());

    countQuery.append(where);
    selectQuery.append(where + " ORDER BY comment.commentSeq DESC");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<CommentVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, CommentVo.class);
    return resultPage;
  }
}

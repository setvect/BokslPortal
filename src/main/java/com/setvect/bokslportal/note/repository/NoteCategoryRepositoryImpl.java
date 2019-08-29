package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteCategorySearch;
import com.setvect.bokslportal.note.vo.NoteCategoryVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;

/**
 * 복슬노트 카테고리
 */
public class NoteCategoryRepositoryImpl implements NoteCategoryRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<NoteCategoryVo> getNoteCategoryPagingList(NoteCategorySearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select category FROM NoteCategoryVo category");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM NoteCategoryVo category");

    StringBuffer where = new StringBuffer(" WHERE category.deleteF = 'N'");

    countQuery.append(where);
    selectQuery.append(where + " order by name");

    PageQueryCondition pageQuery = new PageQueryCondition(Collections.emptyMap(), searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<NoteCategoryVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, NoteCategoryVo.class);
    return resultPage;
  }
}

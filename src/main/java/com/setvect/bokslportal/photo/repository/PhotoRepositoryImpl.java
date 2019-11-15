package com.setvect.bokslportal.photo.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.photo.service.PhotoSearch;
import com.setvect.bokslportal.photo.vo.PhotoVo;
import com.setvect.bokslportal.util.StringEtcUtil;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 사진 검색 조건
 */
public class PhotoRepositoryImpl implements PhotoRepositoryCustom {
  /**
   * JPA DB 세션
   */
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<PhotoVo> getPagingList(final PhotoSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select photo FROM PhotoVo photo");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM PhotoVo photo");

    StringBuffer where = new StringBuffer(" WHERE 1 = 1 ");
    Map<String, Object> bindParameter = new HashMap<>();

    if (StringUtils.isNotBlank(searchCondition.getMemo())) {
      where.append(" AND photo.memo like :memo");
      bindParameter.put("memo", StringEtcUtil.getSqlStringLike(searchCondition.getMemo()));
    }

    if (searchCondition.getDateType() == PhotoSearch.DateType.NOTHING) {
      where.append(" AND photo.shotDate IS NULL ");
    } else if (searchCondition.getDateType() == PhotoSearch.DateType.SELECT) {
      where.append(" AND photo.shotDate BETWEEN :from and :to ");
      bindParameter.put("from", searchCondition.getFrom());
      bindParameter.put("to", searchCondition.getEndLast());
    }

    countQuery.append(where);
    selectQuery.append(where + " ORDER BY photo.shotDate DESC");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<PhotoVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, PhotoVo.class);
    return resultPage;
  }
}

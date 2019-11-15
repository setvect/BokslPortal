package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.board.service.BoardManagerSearch;
import com.setvect.bokslportal.board.vo.BoardManagerVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 게시판관리 Repository
 */
class BoardManagerRepositoryImpl implements BoardManagerRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<BoardManagerVo> getPagingList(BoardManagerSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select b FROM BoardManagerVo b");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM BoardManagerVo b");

    Map<String, Object> bindParameter = new HashMap<>();
    StringBuffer where = new StringBuffer(" WHERE b.deleteF = 'N'");

    // 두개 이상 동시에 검색 조건에 포함 될 수 없음
    if (!StringUtils.isEmpty(searchCondition.getWord())) {
      if (searchCondition.getField() == BoardManagerSearch.Field.name) {
        where.append(" and upper(b.name) like :word");
      } else {
        where.append(" and upper(b.boardCode) like :word");
      }
      bindParameter.put("word", ApplicationUtil.makeLikeString(searchCondition.getWord()).toUpperCase());
    }

    countQuery.append(where);
    selectQuery.append(where + " order by b.boardCode ");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<BoardManagerVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, BoardManagerVo.class);
    return resultPage;
  }

}

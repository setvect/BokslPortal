package com.setvect.bokslportal.board.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.setvect.bokslportal.note.vo.NoteVo;
import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.board.service.BoardArticleSearch;
import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

/**
 * 게시물 Repository
 */
public class BoardArticleRepositoryImpl implements BoardArticleRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

  @Override
  public GenericPage<BoardArticleVo> getPagingList(BoardArticleSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select b FROM BoardArticleVo b");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM BoardArticleVo b");

    Map<String, Object> bindParameter = new HashMap<>();
    StringBuffer where = new StringBuffer(" WHERE b.deleteF = 'N'");
    where.append(" and b.boardManager.boardCode = :boardCode");
    bindParameter.put("boardCode", searchCondition.getBoardCode());

    // 두개 이상 동시에 검색 조건에 포함 될 수 없음
    if (!StringUtils.isEmpty(searchCondition.getWord())) {
      if (searchCondition.getField() == BoardArticleSearch.Field.title) {
        where.append(" and upper(b.title) like :word");
      } else {
        where.append(" and upper(b.content) like :word");
      }
      bindParameter.put("word", ApplicationUtil.makeLikeString(searchCondition.getWord()).toUpperCase());
    }

    countQuery.append(where);
    selectQuery.append(where + " order by b.boardArticleSeq desc");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<BoardArticleVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, BoardArticleVo.class);
    return resultPage;
  }


}

package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.vo.NoteVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 복슬 노트
 */
public class NoteRepositoryImpl implements NoteRepositoryCustom {
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<NoteVo> getPagingList(NoteSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select n FROM NoteVo n");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM NoteVo n");

    Map<String, Object> bindParameter = new HashMap<>();
    StringBuffer where = new StringBuffer(" WHERE n.deleteF = 'N'");
    where.append("and n.categorySeq in (select categorySeq from NoteCategoryVo c where c.deleteF = 'N')");
    if (searchCondition.getCategorySeq() != 0) {
      where.append(" and n.categorySeq = :category");
      bindParameter.put("category", searchCondition.getCategorySeq());
    }

    // 두개 이상 동시에 검색 조건에 포함 될 수 없음
    if (!StringUtils.isEmpty(searchCondition.getWord())) {
      if (searchCondition.getField() == NoteSearch.Field.title) {
        where.append(" and upper(n.title) like :word");
      } else {
        where.append(" and upper(n.content) like :word");
      }
      bindParameter.put("word", ApplicationUtil.makeLikeString(searchCondition.getWord()).toUpperCase());
    }

    countQuery.append(where);
    selectQuery.append(where + getOrder(searchCondition));

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<NoteVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, NoteVo.class);
    return resultPage;
  }

  private String getOrder(NoteSearch searchCondition) {
    if (searchCondition.getSort() == NoteSearch.NoteSort.REG) {
      return " order by n.regDate desc";
    } else {
      return " order by n.editDate desc";
    }
  }
}

package com.setvect.bokslportal.knowledge.repository;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.knowledge.service.KnowledgeSearch;
import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 게시물 Repository
 */
public class KnowledgeRepositoryImpl implements KnowledgeRepositoryCustom {
  /**
   * 쿼리 실행
   */
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<KnowledgeVo> getPagingList(final KnowledgeSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select knowledge FROM KnowledgeVo knowledge");
    StringBuffer countQuery = new StringBuffer("select count(*) FROM KnowledgeVo knowledge");

    StringBuffer where = new StringBuffer(" WHERE knowledge.deleteF = 'N'");
    Map<String, Object> bindParameter = new HashMap<>();

    String searchWord = searchCondition.getWord();
    String searchClassify = searchCondition.getClassifyC();

    if (StringUtils.isNotBlank(searchWord)) {
      where.append(" and ( upper(knowledge.problem) like :word OR upper(knowledge.solution) like :word)");
      bindParameter.put("word", ApplicationUtil.makeLikeString(searchWord.toUpperCase()));
    }
    if (StringUtils.isNotEmpty(searchClassify)) {
      where.append("  and knowledge.classifyC = :classify");
      bindParameter.put("classify", searchClassify);
    }

    countQuery.append(where);
    selectQuery.append(where + " order by knowledgeSeq desc");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<KnowledgeVo> resultPage = PageUtil.excutePageQuery(em, pageQuery, KnowledgeVo.class);
    return resultPage;
  }
}

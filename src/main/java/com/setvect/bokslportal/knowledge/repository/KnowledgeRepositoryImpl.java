package com.setvect.bokslportal.knowledge.repository;

import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.knowledge.service.KnowledgeSearch;
import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

/**
 * 게시물 Repository
 */
public class KnowledgeRepositoryImpl implements KnowledgeRepositoryCustom {
	/** 쿼리 실행 */
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<KnowledgeVo> getKnowledgePagingList(final KnowledgeSearch pageCondition) {
		String where = " where b.deleteF = 'N' ";

		String searchWord = pageCondition.getSearchWord();
		String searchClassify = pageCondition.getSearchClassifyC();

		if (StringUtils.isNotEmpty(searchWord)) {
			String wordLikeString = ApplicationUtil.makeLikeString(searchWord);
			where += " and ( upper(b.problem) like " + wordLikeString.toUpperCase() + " OR upper(b.solution) like "
					+ wordLikeString.toUpperCase() + " )";
		}
		if (StringUtils.isNotEmpty(searchClassify)) {
			where += " and b.classifyC = " + ApplicationUtil.getSqlString(searchClassify);
		}

		PageQueryCondition pageQuery = new PageQueryCondition(Collections.emptyMap(), pageCondition);
		pageQuery.setCountQuery("select count(*) from KnowledgeVo b " + where);
		pageQuery.setSelectQuery("select b from KnowledgeVo b " + where + " order by knowledgeSeq desc");

		GenericPage<KnowledgeVo> result = PageUtil.excutePageQuery(em, pageQuery, KnowledgeVo.class);
		return result;
	}
}

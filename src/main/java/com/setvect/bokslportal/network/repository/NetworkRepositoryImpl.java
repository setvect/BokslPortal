package com.setvect.bokslportal.network.repository;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.service.NetworkSearch;
import com.setvect.bokslportal.network.vo.NetworkVo;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 네트워크 Repository
 */
public class NetworkRepositoryImpl implements NetworkRepositoryCustom {
	/** 쿼리 실행 */
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<NetworkVo> getNetworkPagingList(final NetworkSearch pageCondition) {
		String q = "select count(*) from NetworkVo b " + getArticleWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from NetworkVo b " + getArticleWhereClause(pageCondition) + " order by editDate desc";

		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NetworkVo> resultList = query.getResultList();

		GenericPage<NetworkVo> resultPage = new GenericPage<NetworkVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * 검색 조건
	 *
	 * @param search
	 *            검색 조건
	 * @return where절
	 */
	private String getArticleWhereClause(final NetworkSearch search) {
		String where = " where b.deleteF = 'N' ";

		String searchTitle = search.getSearchTitle();

		if (StringUtils.isNotEmpty(searchTitle)) {
			String wordLikeString = ApplicationUtil.makeLikeString(searchTitle);
			where += " and  upper(b.problem) like " + wordLikeString.toUpperCase();
		}
		return where;
	}
}

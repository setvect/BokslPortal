package com.setvect.bokslportal.board.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.board.service.BoardManagerSearch;
import com.setvect.bokslportal.board.vo.BoardVo;
import com.setvect.bokslportal.common.GenericPage;

/**
 * 게시물 Repository
 */
class BoardRepositoryImpl implements BoardRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<BoardVo> getBoardPagingList(BoardManagerSearch pageCondition) {
		String q = "select count(*) from BoardVo b " + getManagerWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from BoardVo b " + getManagerWhereClause(pageCondition) + " order by b.boardCode ";
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<BoardVo> resultList = query.getResultList();

		GenericPage<BoardVo> resultPage = new GenericPage<BoardVo>(resultList, pageCondition.getStartCursor(),
				totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * @param pageCondition
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getManagerWhereClause(BoardManagerSearch pageCondition) {
		String where = "where b.deleteF = 'N'";

		// 두개가 동새에 검색 조건에 포함 될 수 없음
		if (!StringUtils.isEmpty(pageCondition.getSearchCode())) {
			where += " and upper(b.boardCode) like "
					+ ApplicationUtil.makeLikeString(pageCondition.getSearchCode()).toUpperCase();
		} else if (!StringUtils.isEmpty(pageCondition.getSearchName())) {
			where += " and upper(b.name) like "
					+ ApplicationUtil.makeLikeString((pageCondition.getSearchName())).toUpperCase();
		}
		return where;
	}

}

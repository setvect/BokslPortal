package com.setvect.bokslportal.util.page;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.setvect.bokslportal.common.GenericPage;

/**
 * 패이징 처리
 */
public abstract class PageUtil {

	/**
	 * 페이징 쿼리
	 *
	 * @param em
	 *            EntityManager
	 * @param condition
	 *            검색 조건
	 * @param classType
	 *            클래스 유형
	 * @param <T>
	 *            쿼리 실행후 반환 타입
	 * @return 페이징 결과 값
	 */
	public static <T> GenericPage<T> excutePageQuery(final EntityManager em, final PageQueryCondition condition,
			final Class<T> classType) {

		TypedQuery<Long> queryCount = PageUtil.makeListQueryWhere(em, condition.getCountQuery(),
				condition.getBindParameter(), Long.class);
		int totalCount = queryCount.getSingleResult().intValue();

		TypedQuery<T> querySelect = PageUtil.makeListQueryWhere(em, condition.getSelectQuery(),
				condition.getBindParameter(), classType);

		querySelect.setFirstResult(condition.getPageRange().getStartCursor());
		querySelect.setMaxResults(condition.getPageRange().getReturnCount());

		List<T> resultList = querySelect.getResultList();
		GenericPage<T> resultPage = new GenericPage<>(resultList, condition.getPageRange().getStartCursor(), totalCount,
				condition.getPageRange().getReturnCount());
		return resultPage;
	}

	/**
	 * @param em
	 *            EntityManager
	 * @param queryClause
	 *            실행 쿼리문
	 * @param bindParameter
	 *            쿼리 실행 시 바인딩될 파라미터
	 * @param classType
	 *            반환 타입
	 * @param <T>
	 *            쿼리 실행후 반환 타입
	 * @return Where조건이 포함된 질의
	 */
	private static <T> TypedQuery<T> makeListQueryWhere(final EntityManager em, final String queryClause,
			final Map<String, Object> bindParameter, final Class<T> classType) {
		TypedQuery<T> query = em.createQuery(queryClause, classType);
		bindParameter.entrySet().forEach(bind -> {
			query.setParameter(bind.getKey(), bind.getValue());
		});
		return query;
	}
}

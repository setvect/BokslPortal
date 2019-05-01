package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.board.service.BoardArticleSearch;
import com.setvect.bokslportal.board.vo.BoardArticleVo;
import com.setvect.bokslportal.common.GenericPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 게시물 Repository
 */
public class BoardArticleRepositoryImpl implements BoardArticleRepositoryCustom {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private BoardArticleRepository boardArticle;

	// TODO 목록 검색시 불필요한 항목(내용 TEXT)까지 가져오는 경우 발생. 성능 문제 발생시 수정
	@Override
	public GenericPage<BoardArticleVo> getArticlePagingList(BoardArticleSearch pageCondition) {

		String q = "select count(*) from BoardArticleVo b " + getArticleWhereClause(pageCondition);
		Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select b from BoardArticleVo b " + getArticleWhereClause(pageCondition) + getOrder(pageCondition);

		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<BoardArticleVo> resultList = query.getResultList();

		GenericPage<BoardArticleVo> resultPage = new GenericPage<BoardArticleVo>(resultList,
				pageCondition.getStartCursor(), totalCount, pageCondition.getReturnCount());
		return resultPage;
	}

	/**
	 * 정렬 조건
	 *
	 * @param search
	 *            검색 조건
	 * @return
	 */
	private String getOrder(BoardArticleSearch search) {
		// 게시판 두개 이상인 경우 계층형 구조로 정렬은 하지 않고 입력순 으로 정렬
		if (search.getSearchCodes() != null) {
			return " order by b.articleSeq desc";
		} else {
			return " order by b.idx2 desc, b.idx3 ASC ";
		}
	}

	/**
	 * @param search
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getArticleWhereClause(BoardArticleSearch search) {
		String where = " where ";
		List<String> codes = search.getSearchCodes();
		if (codes == null) {
			where += " b.boardCode = " + ApplicationUtil.getSqlString(search.getSearchCode());
		} else {
			where += " b.boardCode in ( '___dummyCode'";
			for (String c : codes) {
				where += "," + ApplicationUtil.getSqlString(c);
			}
			where += " ) ";
		}

		if (!search.isDeleteView()) {
			// 삭제 게시물 보여 주지 않음
			where += " and b.deleteF = 'N' ";
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtils.isEmpty(search.getSearchName())) {
			where += " and upper(b.name) like " + ApplicationUtil.makeLikeString(search.getSearchName()).toUpperCase();
		} else if (!StringUtils.isEmpty(search.getSearchTitle())) {
			where += " and upper(b.title) like " + ApplicationUtil.makeLikeString(search.getSearchTitle()).toUpperCase();
		} else if (!StringUtils.isEmpty(search.getSearchContent())) {
			where += " and upper(b.content) like "
					+ ApplicationUtil.makeLikeString(search.getSearchContent()).toUpperCase();
		}
		return where;
	}

	@Override
	public void insertArticle(BoardArticleVo article) {
		String q;
		// 인덱스 순서
		q = "select COALESCE(max(b.idx1) + 1, 1)  from BoardArticleVo b WHERE b.boardCode = ?";
		Query query = em.createQuery(q);
		query.setParameter(1, article.getBoardCode());
		int idx1 = ((Integer) query.getSingleResult()).intValue();
		article.setIdx1(idx1);

		q = "select COALESCE(max(b.idx2) + 1, 1) from BoardArticleVo b WHERE b.boardCode = ?";
		query = em.createQuery(q);
		query.setParameter(1, article.getBoardCode());
		int idx2 = ((Integer) query.getSingleResult()).intValue();
		article.setIdx2(idx2);

		// 기본 값
		article.setIdx3(1);
		article.setDepthLevel(1);

		em.persist(article);
	}

}

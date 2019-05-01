package com.setvect.bokslportal.note.repository;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.vo.NoteVo;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 복슬 노트
 */
public class NoteRepositoryImpl implements NoteRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition) {
		String q = "select count(*) from NoteVo n " + getNoteWhereClause(pageCondition);
		javax.persistence.Query query = em.createQuery(q);
		int totalCount = ((Long) query.getSingleResult()).intValue();

		q = "select n from NoteVo n " + getNoteWhereClause(pageCondition) + getOrder(pageCondition);
		query = em.createQuery(q);
		query.setFirstResult(pageCondition.getStartCursor());
		query.setMaxResults(pageCondition.getReturnCount());

		@SuppressWarnings("unchecked")
		List<NoteVo> resultList = query.getResultList();

		GenericPage<NoteVo> resultPage = new GenericPage<NoteVo>(resultList, pageCondition.getStartCursor(), totalCount,
				pageCondition.getReturnCount());
		return resultPage;
	}

	private String getOrder(NoteSearch pageCondition) {
		if (pageCondition.getSort() == NoteSearch.NoteSort.UPD) {
			return " order by n.uptDate desc";
		} else {
			return " order by n.regDate asc";
		}
	}

	/**
	 * @param search
	 *            검색 조건
	 * @return select where 절 조건
	 */
	private String getNoteWhereClause(NoteSearch search) {
		String where = " where ";

		// 삭제 게시물 보여 주지 않음
		where += " n.deleteF = 'N' ";
		where += " and n.categorySeq in (select categorySeq from NoteCategoryVo c where c.deleteF = 'N') ";

		if (search.getSearchCategorySeq() != 0) {
			where += " and n.categorySeq = " + search.getSearchCategorySeq();
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtils.isEmpty(search.getSearchTitle())) {
			where += " and upper(n.title) like " + ApplicationUtil.makeLikeString(search.getSearchTitle()).toUpperCase();
		} else if (!StringUtils.isEmpty(search.getSearchContent())) {
			where += " and upper(n.content) like "
					+ ApplicationUtil.makeLikeString(search.getSearchContent()).toUpperCase();
		}
		return where;
	}
}

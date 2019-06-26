package com.setvect.bokslportal.note.repository;

import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.note.service.NoteSearch;
import com.setvect.bokslportal.note.vo.NoteVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

/**
 * 복슬 노트
 */
public class NoteRepositoryImpl implements NoteRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<NoteVo> getNotePagingList(NoteSearch pageCondition) {
		String where = " where ";

		// 삭제 게시물 보여 주지 않음
		where += " n.deleteF = 'N' ";
		where += " and n.categorySeq in (select categorySeq from NoteCategoryVo c where c.deleteF = 'N') ";

		if (pageCondition.getSearchCategorySeq() != 0) {
			where += " and n.categorySeq = " + pageCondition.getSearchCategorySeq();
		}

		// 두개 이상 동시에 검색 조건에 포함 될 수 없음
		if (!StringUtils.isEmpty(pageCondition.getSearchTitle())) {
			where += " and upper(n.title) like "
					+ ApplicationUtil.makeLikeString(pageCondition.getSearchTitle()).toUpperCase();
		} else if (!StringUtils.isEmpty(pageCondition.getSearchContent())) {
			where += " and upper(n.content) like "
					+ ApplicationUtil.makeLikeString(pageCondition.getSearchContent()).toUpperCase();
		}

		PageQueryCondition pageQuery = new PageQueryCondition(Collections.emptyMap(), pageCondition);
		pageQuery.setCountQuery("select count(*) from NoteVo n " + where);
		pageQuery.setSelectQuery("select n from NoteVo n " + where + getOrder(pageCondition));

		GenericPage<NoteVo> result = PageUtil.excutePageQuery(em, pageQuery, NoteVo.class);
		return result;
	}

	private String getOrder(NoteSearch pageCondition) {
		if (pageCondition.getSort() == NoteSearch.NoteSort.UPD) {
			return " order by n.uptDate desc";
		} else {
			return " order by n.regDate asc";
		}
	}
}

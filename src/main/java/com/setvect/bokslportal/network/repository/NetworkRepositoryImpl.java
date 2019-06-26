package com.setvect.bokslportal.network.repository;

import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.ApplicationUtil;
import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.service.NetworkSearch;
import com.setvect.bokslportal.network.vo.NetworkVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;

/**
 * 네트워크 Repository
 */
public class NetworkRepositoryImpl implements NetworkRepositoryCustom {
	/** 쿼리 실행 */
	@PersistenceContext
	private EntityManager em;

	@Override
	public GenericPage<NetworkVo> getNetworkPagingList(final NetworkSearch pageCondition) {
		String where = " where b.deleteF = 'N' ";
		String searchTitle = pageCondition.getSearchTitle();

		if (StringUtils.isNotEmpty(searchTitle)) {
			String wordLikeString = ApplicationUtil.makeLikeString(searchTitle);
			where += " and  upper(b.problem) like " + wordLikeString.toUpperCase();
		}

		PageQueryCondition pageQuery = new PageQueryCondition(Collections.emptyMap(), pageCondition);
		pageQuery.setCountQuery("select count(*) from NetworkVo b " + where);
		pageQuery.setSelectQuery("select b from NetworkVo b " + where + " order by editDate desc");

		GenericPage<NetworkVo> result = PageUtil.excutePageQuery(em, pageQuery, NetworkVo.class);
		return result;
	}

}

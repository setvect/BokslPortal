package com.setvect.bokslportal.network.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.service.NetworkSearch;
import com.setvect.bokslportal.network.vo.NetworkVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 네트워크 Repository
 */
public class NetworkRepositoryImpl implements NetworkRepositoryCustom {
  /**
   * 쿼리 실행
   */
  @PersistenceContext
  private EntityManager em;

  @Override
  public GenericPage<NetworkVo> getPagingList(final NetworkSearch searchCondition) {
    StringBuffer selectQuery = new StringBuffer("select network from NetworkVo network ");
    StringBuffer countQuery = new StringBuffer("select count(*) from NetworkVo network ");

    StringBuffer where = new StringBuffer(" WHERE network.deleteF = 'N'");
    Map<String, Object> bindParameter = new HashMap<>();

    if (StringUtils.isNotEmpty(searchCondition.getWord())) {
      where.append(" and upper(network.title) like :word");
      bindParameter.put("word", "%" + searchCondition.getWord().toUpperCase() + "%");
    }
    countQuery.append(where);
    selectQuery.append(where + " order by editDate desc");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, searchCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<NetworkVo> result = PageUtil.excutePageQuery(em, pageQuery, NetworkVo.class);
    return result;
  }
}

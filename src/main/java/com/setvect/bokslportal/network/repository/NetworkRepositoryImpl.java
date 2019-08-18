package com.setvect.bokslportal.network.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.service.NetworkSearch;
import com.setvect.bokslportal.network.vo.NetworkVo;
import com.setvect.bokslportal.util.page.PageQueryCondition;
import com.setvect.bokslportal.util.page.PageUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
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
  public GenericPage<NetworkVo> getPagingList(final NetworkSearch pageCondition) {
    StringBuffer selectQuery = new StringBuffer("select network from NetworkVo network ");
    StringBuffer countQuery = new StringBuffer("select count(*) from NetworkVo network ");

    StringBuffer where = new StringBuffer(" WHERE network.deleteF = 'N'");
    Map<String, Object> bindParameter = new HashMap<>();

    if (StringUtils.isNotEmpty(pageCondition.getWord())) {
      where.append(" and upper(network.title) like :word");
      bindParameter.put("word", "%" + pageCondition.getWord().toUpperCase() + "%");
    }
    countQuery.append(where);
    selectQuery.append(where + " order by editDate desc");

    PageQueryCondition pageQuery = new PageQueryCondition(bindParameter, pageCondition);
    pageQuery.setCountQuery(countQuery.toString());
    pageQuery.setSelectQuery(selectQuery.toString());

    GenericPage<NetworkVo> result = PageUtil.excutePageQuery(em, pageQuery, NetworkVo.class);
    return result;
  }
}

package com.setvect.bokslportal.network.repository;

import com.setvect.bokslportal.common.GenericPage;
import com.setvect.bokslportal.network.service.NetworkSearch;
import com.setvect.bokslportal.network.vo.NetworkVo;

/**
 * 네트워크 내용
 */
public interface NetworkRepositoryCustom {

	/**
	 * @param pageCondition
	 *            노트 검색 정보
	 * @return 노트 목록
	 */
	public GenericPage<NetworkVo> getPagingList(NetworkSearch pageCondition);

}

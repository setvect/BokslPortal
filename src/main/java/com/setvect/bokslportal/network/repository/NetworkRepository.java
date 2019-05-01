package com.setvect.bokslportal.network.repository;

import com.setvect.bokslportal.network.vo.NetworkVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 네트워크 내용
 */
public interface NetworkRepository extends JpaRepository<NetworkVo, Integer>, NetworkRepositoryCustom {
}

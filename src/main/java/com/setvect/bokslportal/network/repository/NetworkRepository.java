package com.setvect.bokslportal.network.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.network.vo.NetworkVo;

/**
 * 네트워크 내용
 */
public interface NetworkRepository extends JpaRepository<NetworkVo, Integer>, NetworkRepositoryCustom {
}

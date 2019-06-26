package com.setvect.bokslportal.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.setvect.bokslportal.user.vo.UserVo;

/**
 * 사용자 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<UserVo, String> {
}


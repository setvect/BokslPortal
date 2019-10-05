package com.setvect.bokslportal.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.board.vo.BoardManagerVo;

/**
 * 게시판 관리
 */
public interface BoardManagerRepository extends JpaRepository<BoardManagerVo, String>, BoardManagerRepositoryCustom {
}

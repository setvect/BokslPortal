package com.setvect.bokslportal.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.board.vo.BoardVo;

/**
 * 게시판 관리
 */
public interface BoardRepository extends JpaRepository<BoardVo, String>, BoardRepositoryCustom {
}

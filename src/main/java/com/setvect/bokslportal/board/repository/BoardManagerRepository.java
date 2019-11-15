package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.board.vo.BoardManagerVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시판 관리
 */
public interface BoardManagerRepository extends JpaRepository<BoardManagerVo, String>, BoardManagerRepositoryCustom {
}

package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.board.vo.BoardVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시판 관리
 */
public interface BoardRepository extends JpaRepository<BoardVo, String>, BoardRepositoryCustom {
}

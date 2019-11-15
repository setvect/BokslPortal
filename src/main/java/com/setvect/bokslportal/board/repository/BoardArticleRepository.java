package com.setvect.bokslportal.board.repository;

import com.setvect.bokslportal.board.vo.BoardArticleVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시물
 */
public interface BoardArticleRepository extends JpaRepository<BoardArticleVo, Integer>, BoardArticleRepositoryCustom {
}

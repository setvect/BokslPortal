package com.setvect.bokslportal.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.board.vo.BoardArticleVo;

/**
 * 게시물
 */
public interface BoardArticleRepository extends JpaRepository<BoardArticleVo, Integer>, BoardArticleRepositoryCustom {
}

package com.setvect.bokslportal.comment.repository;

import com.setvect.bokslportal.comment.vo.CommentVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentVo, Integer>, CommentRepositoryCustom {
}


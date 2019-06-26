package com.setvect.bokslportal.knowledge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;

public interface KnowledgeRepository extends JpaRepository<KnowledgeVo, Integer>, KnowledgeRepositoryCustom {

}

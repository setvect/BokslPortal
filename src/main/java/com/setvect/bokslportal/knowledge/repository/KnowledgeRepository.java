package com.setvect.bokslportal.knowledge.repository;

import com.setvect.bokslportal.knowledge.vo.KnowledgeVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeRepository extends JpaRepository<KnowledgeVo, Integer>, KnowledgeRepositoryCustom {

}

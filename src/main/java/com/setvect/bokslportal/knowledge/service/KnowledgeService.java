package com.setvect.bokslportal.knowledge.service;

import com.setvect.bokslportal.knowledge.repository.KnowledgeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KnowledgeService {
  @Autowired
  private KnowledgeRepository knowledgeRepository;


}

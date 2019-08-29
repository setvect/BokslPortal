package com.setvect.bokslportal.knowledge.service;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 목록 페이징 및 검색 조건
 */
@Getter
@Setter
public class KnowledgeSearch extends SearchListVo {
  /**
   * 분류 코드
   */
  private String classifyC;
  /**
   * 검색어
   */
  private String word;
}

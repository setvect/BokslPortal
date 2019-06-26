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

	private String searchClassifyC;
	private String searchWord;

	public KnowledgeSearch(int startCursor, int returnCount) {
		super(startCursor, returnCount);
	}
}

package com.setvect.bokslportal.comment.service;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 코멘트 검색 조건
 */
@Getter
@Setter
public class CommentSearch extends SearchListVo {

	private final CommentModule moduleName;

	public CommentSearch(CommentModule moduleName, int startCursor, int returnCount) {
		super(startCursor, returnCount);
		this.moduleName = moduleName;
	}

}

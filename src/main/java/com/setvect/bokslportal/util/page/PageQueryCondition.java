package com.setvect.bokslportal.util.page;

import java.util.Map;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 페이징 조건
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class PageQueryCondition {
	@Getter
	private final Map<String, Object> bindParameter;

	@Getter
	private final SearchListVo pageRange;

	@Getter
	@Setter
	private String selectQuery;

	@Getter
	@Setter
	private String countQuery;
}

package com.setvect.bokslportal.util.page;

import com.setvect.bokslportal.common.SearchListVo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 페이징 조건
 */
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PageQueryCondition {
  private final Map<String, Object> bindParameter;

  private final SearchListVo pageRange;

  private String selectQuery;

  private String countQuery;
}

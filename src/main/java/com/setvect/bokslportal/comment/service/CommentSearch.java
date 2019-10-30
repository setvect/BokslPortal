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
  private CommentModule moduleName;
  private String moduleId;
}

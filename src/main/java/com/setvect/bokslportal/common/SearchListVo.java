package com.setvect.bokslportal.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 페이지 정보를 계산하기위해 사용
 */
@RequiredArgsConstructor
@Getter
@ToString
public class SearchListVo {

  /**
   * 시작 커서 위치(0부터 시작)
   */
  private final int startCursor;
  /**
   * 가져올 항목 갯수
   */
  private final int returnCount;
}

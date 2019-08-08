package com.setvect.bokslportal.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 페이지 정보를 계산하기위해 사용
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchListVo {

  private static final int DEFAUlT_RETRUN_COUNT = 10;
  /**
   * 시작 커서 위치(0부터 시작)
   */
  private int startCursor;
  /**
   * 가져올 항목 갯수
   */
  private int returnCount = DEFAUlT_RETRUN_COUNT;
}

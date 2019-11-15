package com.setvect.bokslportal.photo.service;

import com.setvect.bokslportal.common.SearchListVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * 포토 검색 조건
 */
@Getter
@Setter
public class PhotoSearch extends SearchListVo {
  public enum DateType {
    /**
     * 촬영일짜 조건 없이 전체 검색
     */
    ALL,
    /**
     * 촬영일짜 정보가 없는 사진 검색
     */
    NOTHING,
    /**
     * 촬영일짜 범위 선택
     */
    SELECT
  }

  /**
   * 시작 날짜
   */
  private Date from;

  /**
   * 끝 날짜
   */
  private Date to;

  /**
   * 메모 조건
   */
  private String memo;

  /**
   * 촬영 날짜가 없는 사진 검색
   */
  private DateType dateType;

  /**
   * @return 종료일 + 23:59:59
   */
  public Date getEndLast() {
    Calendar cal = Calendar.getInstance();
    cal.setTime(to);
    cal.add(Calendar.DATE, 1);
    cal.add(Calendar.SECOND, -1);
    return cal.getTime();
  }

  /**
   * @return 날짜 범위 검색 여부
   */
  public boolean isDateBetween() {
    return from != null && to != null;
  }
}

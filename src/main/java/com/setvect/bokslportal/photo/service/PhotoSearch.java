package com.setvect.bokslportal.photo.service;

import java.util.Calendar;
import java.util.Date;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 포토 검색 조건
 */
@Getter
@Setter
public class PhotoSearch extends SearchListVo {

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
  private boolean dateNoting;

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

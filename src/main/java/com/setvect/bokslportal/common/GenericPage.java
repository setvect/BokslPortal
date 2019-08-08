package com.setvect.bokslportal.common;

import java.util.List;

import lombok.Getter;

/**
 * Page에서 검색된 객체들의 타입을 지정함
 */
@Getter
public class GenericPage<T> {

  private final List<T> list;
  private final int startCursor;
  private final int totalCount;
  private final int returnCount;

  /**
   * @param objects
   *          리스트
   * @param startCursor
   *          시작 항목(1부터 시작)
   * @param totalCount
   *          전체 항목 수
   */
  public GenericPage(List<T> list, int startCursor, int totalCount) {
    this.list = list;
    this.startCursor = startCursor;
    this.totalCount = totalCount;
    this.returnCount = 0;
  }

  /**
   * @param objects
   *          리스트
   * @param startCursor
   *          시작 항목(1부터 시작)
   * @param totalCount
   *          전체 항목 수
   */
  public GenericPage(List<T> list, int startCursor, int totalCount, int returnCount) {
    this.list = list;
    this.startCursor = startCursor;
    this.totalCount = totalCount;
    this.returnCount = returnCount;
  }

  /**
   * 전체 페이지 개수
   *
   * @return the returnCount
   */
  public int getPageCount() {
    if (returnCount == 0) {
      return 0;
    }
    int page = (int) Math.ceil((double) totalCount / returnCount);
    return page;
  }
}

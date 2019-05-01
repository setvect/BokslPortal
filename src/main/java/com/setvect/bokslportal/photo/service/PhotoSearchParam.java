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
public class PhotoSearchParam extends SearchListVo {

	/** 시작 날짜 */
	private Date searchFrom;

	/** 끝 날짜 */
	private Date searchTo;

	/** 메모 조건 */
	private String searchMemo;

	/** 디레토리 조건 */
	private String searchDirectory;

	/** 촬영 날짜가 없는 사진 검색 */
	private boolean searchDateNoting;

	/** 분류 조건 */
	private int searchFolderSeq;

	/** 그룹 분류 */
	private DateGroup searchDateGroup;

	/**
	 * @param startCursor
	 *            시작 지점 (0부터 시작)
	 * @param returnCount
	 *            가져올 항목 갯수
	 */
	public PhotoSearchParam(final int startCursor, final int returnCount) {
		super(startCursor, returnCount);
	}

	/**
	 * @return 종료일 + 23:59:59
	 */
	public Date getSearchToEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(searchTo);
		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.SECOND, -1);
		return cal.getTime();
	}

  /**
   * @return 날짜 범위 검색 여부
   */
  public boolean isDateBetween() {
    return searchFrom != null && searchTo != null;
  }

}

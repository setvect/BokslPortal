package com.setvect.bokslportal.note.service;

import org.apache.commons.lang3.StringUtils;

import com.setvect.bokslportal.common.SearchListVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 노트 페이징 및 검색 조건
 */
@Getter
@Setter
@ToString
public class NoteSearch extends SearchListVo {
  public enum NoteSort {
    REG, UPD;
  }

  private int categorySeq;
  private String title;
  private String content;
  private NoteSort sort;

  /**
   * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
   */
  public String getWord() {
    if (!StringUtils.isEmpty(title)) {
      return title;
    }
    if (!StringUtils.isEmpty(content)) {
      return content;
    }
    return null;
  }
}

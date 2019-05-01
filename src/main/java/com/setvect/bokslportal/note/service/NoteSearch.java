package com.setvect.bokslportal.note.service;

import com.setvect.bokslportal.common.SearchListVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

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

  private int searchCategorySeq;
  private String searchTitle;
  private String searchContent;
  private NoteSort sort;

  public NoteSearch(int startCursor, int returnCount) {
    super(startCursor, returnCount);
  }

  /**
   * @return 검색 단어중 값이 있는 하나를 반환. 아무도 없으면 null. 2개 이상 값이 있을 경우 어떤걸 반환 할지 모름
   */
  public String getWord() {
    if (!StringUtils.isEmpty(searchTitle)) {
      return searchTitle;
    }
    if (!StringUtils.isEmpty(searchContent)) {
      return searchContent;
    }
    return null;
  }
}

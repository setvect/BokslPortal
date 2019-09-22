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
  /**
   * 정렬 방식
   */
  public enum NoteSort {
    /**
     * 등록일 기준 내림차순
     */
    REG,
    /**
     * 마지막 수정 기준 내림차순(기본값)
     */
    EDIT;
  }

  public enum Field {
    title,
    content,
  }

  private int categorySeq;
  private Field field;
  private String word;
  private NoteSort sort;

}

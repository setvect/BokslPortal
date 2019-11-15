package com.setvect.bokslportal.memo.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 메모장 카테고리 정보
 */
@Entity
@Table(name = "TBCA_MEMO_CATEGORY")
@Getter
@Setter
public class MemoCategoryVo {
  /**
   * 일련번호
   */
  @Id
  @Column(name = "CATEGORY_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int categorySeq;

  /**
   * 이름
   */
  @Column(name = "NAME", nullable = false, length = 200)
  private String name;
}

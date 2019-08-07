package com.setvect.bokslportal.todo.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TBHA_TODO")
/**
 * TODO 항목
 */
public class TodoVo {

  /**
   * TODO 반복 주기
   */
  public enum Period{
    ONCE, DAY, WEEK, MONTH
  }

  /**
   * 일련번호
   */
  @Id
  @Column(name = "TODO_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int todoSeq;

  /**
   * 내용
   */
  @Column(name = "CONTENT", nullable = false, length = 1000)
  private String content;

  /**
   * 주기
   */
  @Column(name = "PERIOD", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private Period period;

  /**
   * 시작기간
   */
  @Column(name = "DURATION_FROM", nullable = true)
  private Date durationFrom;

  /**
   * 종료기간
   */
  @Column(name = "DURATION_TO", nullable = true)
  private Date durationTo;

  /**
   * 등록일
   */
  @Column(name = "REG_DATE", nullable = false)
  private Date regDate;

  /**
   * 삭제여부
   */
  @Column(name = "DELETE_F", nullable = false)
  @Type(type = "yes_no")
  private boolean deleteF;

  /**
   * 체크 항목
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "todo", cascade = CascadeType.ALL)
  private List<TodoCheckVo> todoCheckList;
}

package com.setvect.bokslportal.todo.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBHB_TODO_CHECK")
/**
 * TODO 체크 정보
 */
public class TodoCheckVo {
  /**
   * 체크 유형
   */
  public enum CheckType {
    /**
     * 수행
     */
    DO,
    /**
     * 수행 안함.
     */
    NOT_DO
  }

  @Id
  @Column(name = "TODO_CHEK_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int todoCheckSeq;

  /**
   * 연관 TODO
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TODO_SEQ", nullable = false)
  private TodoVo todo;

  /**
   * 체크 유형
   */
  @Column(name = "CHECK_TYPE", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private CheckType checkType;

  /**
   * 체크일
   */
  @Column(name = "CHECK_DATE", nullable = false)
  private Date checkDate;


}

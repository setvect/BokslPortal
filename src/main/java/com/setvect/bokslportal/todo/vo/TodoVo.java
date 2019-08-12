package com.setvect.bokslportal.todo.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TBHA_TODO")
/**
 * 할일 항목
 */
public class TodoVo {

  /**
   * 체크 유형
   */
  public enum StatusType {
    /**
     * 계획
     */
    PLAN,
    /**
     * 포기
     */
    GIVEUP,
    /**
     * 완료
     */
    COMPLETE;
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
   * 체크 유형
   */
  @Column(name = "STATUS_TYPE", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private StatusType statusType;

  /**
   * 체크일
   */
  @Column(name = "CHECK_DATE", nullable = true)
  private Date checkDate;

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
}

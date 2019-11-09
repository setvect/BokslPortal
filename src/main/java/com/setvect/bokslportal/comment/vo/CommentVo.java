package com.setvect.bokslportal.comment.vo;

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

import com.setvect.bokslportal.comment.service.CommentModule;
import com.setvect.bokslportal.user.vo.UserVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 코멘트
 */
@Entity
@Table(name = "TBYB_COMMENT")
@Getter
@Setter
public class CommentVo {

  /**
   * 일련번호
   */
  @Id
  @Column(name = "COMMENT_SEQ")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int commentSeq;

  /**
   * 모듈이름
   */
  @Column(name = "MODULE_NAME", nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private CommentModule moduleName;

  /**
   * 모듈 아이디
   */
  @Column(name = "MODULE_ID", nullable = false, length = 50)
  private String moduleId;

  /**
   * 아이디
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER", nullable = false)
  private UserVo user;

  /**
   * 내용
   */
  @Column(name = "CONTENT", nullable = false, length = 4000)
  private String content;

  /**
   * 등록일
   */
  @Column(name = "REG_DATE", nullable = false)
  private Date regDate;

}

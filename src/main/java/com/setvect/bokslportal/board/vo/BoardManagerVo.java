package com.setvect.bokslportal.board.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 게시판 설정
 */
@Entity
@Table(name = "TBBA_BOARD_MANAGER")
@Getter
@Setter
public class BoardManagerVo {
  /**
   * 게시판 코드
   */
  @Id
  @Column(name = "BOARD_CODE", nullable = false, length = 20)
  private String boardCode;

  /**
   * 게시판 이름
   */
  @Column(name = "NAME", nullable = false, length = 50)
  private String name;

  /**
   * 총 게시물 파일 업로드 제한
   */
  @Column(name = "UPLOAD_LIMIT", nullable = false)
  private int uploadLimit;

  /**
   * 답변 여부
   */
  @Column(name = "REPLY_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean replyF;

  /**
   * 코멘트 여부
   */
  @Column(name = "COMMENT_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean commentF;

  /**
   * 자료실 여부
   */
  @Column(name = "ATTACH_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean attachF;

  /**
   * 암호화 게시물 허용 여부
   */
  @Column(name = "ENCRYPT_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean encryptF;

  /**
   * 게시판 삭제 여부
   */
  @Column(name = "DELETE_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean deleteF;

}

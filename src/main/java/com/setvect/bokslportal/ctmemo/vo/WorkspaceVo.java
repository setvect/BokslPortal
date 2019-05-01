package com.setvect.bokslportal.ctmemo.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 메모장 워크스페이스 정보
 */
@Entity
@Table(name = "TBCB_WORKSPACE")
@Getter
@Setter
public class WorkspaceVo {
  /**
   * 일련번호
   */
  @Id
  @Column(name = "WORKSPACE_SEQ", nullable = false)
  @GenericGenerator(name = "hibernate-increment", strategy = "increment")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
  private int workspaceSeq;

  /**
   * 내용
   */
  @Column(name = "TITLE", nullable = false, length = 200)
  private String title;
}

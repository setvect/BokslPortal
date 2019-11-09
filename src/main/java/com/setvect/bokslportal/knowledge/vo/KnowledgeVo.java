package com.setvect.bokslportal.knowledge.vo;

import com.setvect.bokslportal.attach.vo.AttachFileVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 지식정보
 */
@Entity
@Table(name = "TBEA_KNOWLEDGE")
@Getter
@Setter
public class KnowledgeVo {
  /**
   * key
   */
  @Id
  @Column(name = "KNOWLEDGE_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int knowledgeSeq;

  /**
   * 분류 코드
   */
  @Column(name = "CLASSIFY_C", nullable = false, length = 20)
  private String classifyC;

  /**
   * 문제
   */
  @Column(name = "PROBLEM", nullable = false)
  @Lob
  private String problem;

  /**
   * 해법
   */
  @Column(name = "SOLUTION", nullable = true)
  @Lob
  private String solution;

  /**
   * 등록일
   */
  @Column(name = "REG_DATE", nullable = false)
  private Date regDate;

  /**
   * 삭제여부
   */
  @Column(name = "DELETE_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean deleteF;


  /**
   * 첨부파일
   */
  @Transient
  private List<AttachFileVo> attach;

}

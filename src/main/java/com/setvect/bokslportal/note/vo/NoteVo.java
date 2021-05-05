package com.setvect.bokslportal.note.vo;

import com.setvect.bokslportal.attach.vo.AttachFileVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 노트 내용
 */
@Entity
@Table(name = "TBDB_NOTE")
@Getter
@Setter
public class NoteVo {

  /**
   * 일련번호
   */
  @Id
  @Column(name = "NOTE_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int noteSeq;

  /**
   * 본 필드 필요하다. 지우지 마라<br>
   * NoteCategoryVo 값 넣어도 Insert 시점에서 등록 안된다.
   */
  @Column(name = "CATEGORY_SEQ", nullable = false)
  private int categorySeq;

  /**
   * 카테고리
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_SEQ", insertable = false, updatable = false)
  private NoteCategoryVo category;

  /**
   * 제목
   */
  @Column(name = "TITLE", nullable = false, length = 200)
  private String title;

  /**
   * 내용
   */
  @Column(name = "CONTENT", nullable = false)
  @Lob
  private String content;

  /**
   * 마지막 수정일
   */
  @Column(name = "EDIT_DATE", nullable = true)
  private Date editDate;

  /**
   * 처음 등록일
   */
  @Column(name = "REG_DATE", nullable = false)
  private Date regDate;

  /**
   * 마크다운 문서 여부
   */
  @Column(name = "MARKDOWN_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean markdownF;

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

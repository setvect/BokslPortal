package com.setvect.bokslportal.note.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 노트 카테고리
 */
@Entity
@Table(name = "TBDA_NOTE_CATEGORY")
@Getter
@Setter
public class NoteCategoryVo {
  /**
   * 일련번호
   */
  @Id
  @Column(name = "CATEGORY_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int categorySeq;

  /**
   * 부모 카테고리 아이디
   */
  @ManyToOne
  @JoinColumn(name = "PARENT_ID", nullable = false)
  private NoteCategoryVo parent;

  /**
   * 자식<br>
   */
  @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
  private List<NoteCategoryVo> children;

  /**
   * 이름
   */
  @Column(name = "NAME", nullable = false, length = 50)
  private String name;

  /**
   * 처음 등록일
   */
  @Column(name = "REG_DATE", nullable = false)
  private Date regDate;

  /**
   * 삭제여부
   */
  @Column(name = "DELETE_F", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean deleteF;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NoteCategoryVo that = (NoteCategoryVo) o;
    return categorySeq == that.categorySeq;
  }

  @Override
  public int hashCode() {
    return Objects.hash(categorySeq);
  }

  /**
   * @return true 루트 카테고리
   */
  public boolean isRoot() {
    if (this.getParent() == null) {
      return false;
    }
    return this.getCategorySeq() == this.getParent().getCategorySeq();
  }
}

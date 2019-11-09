package com.setvect.bokslportal.migration;

import com.setvect.bokslportal.note.vo.NoteCategoryVo;

import java.util.Date;
import java.util.List;

public class CategoryTemp {
  private int categorySeq;

  private int parentId;

  private List<NoteCategoryVo> children;

  private String name;

  private Date regDate;

  private boolean deleteF;

  public int getCategorySeq() {
    return categorySeq;
  }

  public void setCategorySeq(int categorySeq) {
    this.categorySeq = categorySeq;
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }

  public List<NoteCategoryVo> getChildren() {
    return children;
  }

  public void setChildren(List<NoteCategoryVo> children) {
    this.children = children;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getRegDate() {
    return regDate;
  }

  public void setRegDate(Date regDate) {
    this.regDate = regDate;
  }

  public boolean isDeleteF() {
    return deleteF;
  }

  public void setDeleteF(boolean deleteF) {
    this.deleteF = deleteF;
  }
}

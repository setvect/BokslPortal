package com.setvect.bokslportal.common;

/**
 * tree구조를 나타내기 위한 기본 인터페이스 항목 정보<br>
 * 트리 구조를 표현하기 위해서는 본 인터페이스를 구현 해야 됨 .
 */
public interface TreeItem<ID> extends Comparable<TreeItem<ID>> {
  /**
   * @return Returns the id.
   */
  ID getId();

  /**
   * @return Returns the parent.
   */
  ID getParentId();

  /**
   * 카테고리 단계. 0 부터 시작
   *
   * @param depth The depth to set.
   */
  void setDepth(int depth);
}

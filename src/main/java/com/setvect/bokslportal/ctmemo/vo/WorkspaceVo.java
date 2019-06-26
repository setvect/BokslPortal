package com.setvect.bokslportal.ctmemo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int workspaceSeq;

	/**
	 * 내용
	 */
	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;
}

package com.setvect.bokslportal.ctmemo.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 메모장
 */
@Entity
@Table(name = "TBCA_CTMEMO")
@Getter
@Setter
public class CtmemoVo implements Serializable {
	private static final long serialVersionUID = 4659097867395968759L;

	/** 일련번호 */
	@Id
	@Column(name = "CTMEMO_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int ctmemoSeq;

	/** 내용 */
	@Column(name = "CONTENT", nullable = false, length = 4000)
	private String content;

	/** 글자색 css */
	@Column(name = "FONT_CSS", nullable = false, length = 20)
	private String fontCss;

	/** 배경색 css */
	@Column(name = "BG_CSS", nullable = false, length = 20)
	private String bgCss;

	/** HTML 레이어에서 Z-INDEX */
	@Column(name = "Z_INDEX", nullable = false)
	private int zIndex;

	/** 넓이(픽셀) */
	@Column(name = "WIDTH", nullable = false)
	private int width;

	/** 높이(픽셀 */
	@Column(name = "HEIGHT", nullable = false)
	private int height;

	/** 좌표 X */
	@Column(name = "POSITION_X", nullable = false)
	private int positionX;

	/** 좌표 Y */
	@Column(name = "POSITION_Y", nullable = false)
	private int positionY;

	/** 마지막 수정일 */
	@Column(name = "UPT_DATE", nullable = true)
	private Date uptDate;

	/** 처음 등록일 */
	@Column(name = "REG_DATE", nullable = true)
	private Date regDate;

	/** 삭제여부 */
	@Column(name = "DELETE_F", nullable = false)
	@Type(type = "yes_no")
	private boolean deleteF;

	/** 워크스페이스 일련번호 */
	@Column(name = "WORKSPACE_SEQ", nullable = false)
	private int workspaceSeq;
}

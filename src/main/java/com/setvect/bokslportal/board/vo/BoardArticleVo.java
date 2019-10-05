package com.setvect.bokslportal.board.vo;

import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Type;

import com.setvect.bokslportal.attach.vo.AttachFileVo;
import com.setvect.bokslportal.user.vo.UserVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시물 VO
 */
@Entity
@Table(name = "TBBB_BOARD_ARTICLE")
@Getter
@Setter
public class BoardArticleVo {
	/** */
	@Id
	@Column(name = "ARTICLE_SEQ", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int articleSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARD_CODE", nullable = false)
	private BoardManagerVo board;

	/** */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserVo user;

	/** */
	@Column(name = "IDX1", nullable = false)
	private int idx1;

	/** */
	@Column(name = "IDX2", nullable = false)
	private int idx2;

	/** */
	@Column(name = "IDX3", nullable = false)
	private int idx3;

	/** 깊이 1부터 시작 */
	@Column(name = "DEPTH_LEVEL", nullable = false)
	private int depthLevel;

	/** 제목 */
	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	/** 본문 */
	@Column(name = "CONTENT", nullable = false)
	@Lob
	private String content;

	/** 아이피 */
	@Column(name = "IP", nullable = false, length = 20)
	private String ip;

	/** 조회수 */
	@Column(name = "HIT_COUNT", nullable = false)
	private int hitCount;

	/** 암호화된 글 여부 */
	@Column(name = "ENCODE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean encodeF;

	/** */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/** 게시물 삭제 여부 */
	@Column(name = "DELETE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean deleteF;

	/** 첨부파일 */
	@Transient
	private List<AttachFileVo> attach;

}

package com.setvect.bokslportal.comment.vo;

import com.setvect.bokslportal.comment.service.CommentModule;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 코멘트
 */
@Entity
@Table(name = "TBGA_COMMENT")
@Getter
@Setter
public class CommentVo {

	/** 일련번호 */
	@Id
	@Column(name = "COMMENT_SEQ")
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int commentSeq;

	/** 모듈이름 */
	@Column(name = "MODULE_NAME")
	@Enumerated(EnumType.STRING)
	private CommentModule moduleName;

	/** 모듈 아이디 */
	@Column(name = "MODULE_ID")
	private String moduleId;

	/** 아이디 */
	@Column(name = "USER_ID")
	private String userId;

	/** 내용 */
	@Column(name = "CONTENT")
	private String content;

	/** 등록일 */
	@Column(name = "REG_DATE")
	private Date regDate;


}

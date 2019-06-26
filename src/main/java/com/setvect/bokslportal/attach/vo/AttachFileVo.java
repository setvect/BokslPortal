package com.setvect.bokslportal.attach.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.setvect.bokslportal.service.vo.AttachFileModule;
import com.setvect.bokslportal.user.vo.UserVo;

import lombok.Getter;
import lombok.Setter;

/**
 * 첨부파일
 */
@Entity
@Table(name = "TBYA_ATTACH_FILE")
@Setter
@Getter
public class AttachFileVo {

	/** 일련번호 */
	@Id
	@Column(name = "ATTACH_FIlE_SEQ", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int attachFileSeq;

	/** 모듈이름 */
	@Column(name = "MODULE_NAME", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private AttachFileModule moduleName;

	/** 모듈내 항목 번호 */
	@Column(name = "MODULE_ID", nullable = false, length = 50)
	private String moduleId;

	/** 원본파일명 */
	@Column(name = "ORIGINAL_NAME", nullable = false, length = 250)
	private String originalName;

	/** 저장파일명 */
	@Column(name = "SAVE_NAME", nullable = false, length = 250)
	private String saveName;

	/** 파일 사이즈 */
	@Column(name = "SIZE", nullable = false)
	private int size;

	/** 아이디 */
	@Column(name = "USER_ID", nullable = false, length = 20)
	private String userId;

	/** 등록일 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/** 등록자 */
	@Transient
	private UserVo user;
}

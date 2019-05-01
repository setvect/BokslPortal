package com.setvect.bokslportal.code.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 코드
 */
@Entity
@Table(name = "TBYC_CODE")
@Getter
@Setter
public class CodeVo {
	/** 일련번호 */
	@Id
	@Column(name = "CODE_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int codeSeq;

	/** 메인코드 */
	@Column(name = "MAJOR_CODE", nullable = false, length = 20)
	private String majorCode;

	/** 코드 */
	@Column(name = "MINOR_CODE", nullable = false, length = 20)
	private String minorCode;

	/** 코드값 */
	@Column(name = "CODE_VALUE", nullable = false, length = 100)
	private String codeValue;

	/** 순서 */
	@Column(name = "ORDER_NO", nullable = false)
	private int orderNo;

}

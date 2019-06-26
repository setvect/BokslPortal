package com.setvect.bokslportal.photo.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

/**
 * 사진 항목
 */
@Entity
@Table(name = "TBGA_PHOTO", indexes = { @Index(name = "INDEX_SHOT_DATE", columnList = "SHOT_DATE", unique = false) })
@Getter
@Setter
public class PhotoVo {
	/** MD5 값 */
	@Id
	@Column(name = "PHOTO_ID", length = 32)
	private String photoId;

	/** 파일경로 */
	@Column(name = "DIRECTORY", nullable = false, length = 500)
	private String directory;

	/** 파일이름 */
	@Column(name = "NAME", nullable = false, length = 500)
	private String name;

	/** 촬영일 */
	@Column(name = "SHOT_DATE", nullable = true)
	private Date shotDate;

	/** 촬영일 데이터 형태 */
	@Column(name = "SHOT_DATE_TYPE", nullable = true, length = 10)
	@Enumerated(EnumType.STRING)
	private ShotDateType shotDateType;

	/** 메모 */
	@Column(name = "MEMO", nullable = true, length = 500)
	private String memo;

	/** 위도 */
	@Column(name = "LATITUDE", nullable = true)
	private Double latitude;

	/** 경도 */
	@Column(name = "LONGITUDE", nullable = true)
	private Double longitude;

	/** 이미지 회전 메타 정보 */
	@Column(name = "ORIENTATION", nullable = false)
	private int orientation;

	/** 보호 이미지 */
	@Column(name = "PROTECT_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean protectF;

	/** 등록일 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regData;

	/** 이미지를 볼 수 없는 경우 true. 비공개 이미지 이면서 허가된 IP로 접속되지 않는 경우 보이지 않음. */
	@Transient
	private boolean deny;

	/**
	 * 촬영일 입력 데이터 형식
	 */
	public enum ShotDateType {
		/** 메타 정보 추출 */
		META,
		/** 사용자 입력 */
		MANUAL
	}


	/**
	 * @return 이미지 회전여부. true: 회전, false: 회전 안 됨
	 */
	public boolean isRotate() {
		return orientation != 0 && orientation != 1;
	}

}

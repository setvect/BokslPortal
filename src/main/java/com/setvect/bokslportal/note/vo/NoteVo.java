package com.setvect.bokslportal.note.vo;

import com.setvect.bokslportal.attach.vo.AttachFileVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 노트 내용
 */
@Entity
@Table(name = "TBDB_NOTE")
@Getter
@Setter
public class NoteVo implements Serializable {

	/** */
	private static final long serialVersionUID = -2975824645025088507L;

	/** 일련번호 */
	@Id
	@Column(name = "NOTE_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int noteSeq;

	/** 카테고리 */
	@Column(name = "CATEGORY_SEQ", nullable = false)
	private int categorySeq;

	/** 제목 */
	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	/** 내용 */
	@Column(name = "CONTENT", nullable = false)
	@Lob
	private String content;

	/** 마지막 수정일 */
	@Column(name = "UPT_DATE", nullable = true)
	private Date uptDate;

	/** 처음 등록일 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/** 삭제여부 */
	@Column(name = "DELETE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean deleteF;

	/** 첨부파일 */
	@Transient
	private MultipartFile[] attachFile;

	/** 첨부파일 */
	@Transient
	private List<AttachFileVo> attach;


}

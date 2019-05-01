package com.setvect.bokslportal.network.vo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TBFA_NETWORK")
@Getter
@Setter
public class NetworkVo {
	@Id
	@Column(name = "NETWORK_SEQ", nullable = false)
	@GenericGenerator(name = "hibernate-increment", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "hibernate-increment")
	private int networkSeq;
	@Column(name = "TITLE", nullable = false, length = 200)
	private String title;

	@Column(name = "CONTENT", nullable = false)
	@Lob
	private String jsonData;

	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	@Column(name = "EDIT_DATE", nullable = false)
	private Date editDate;

	@Column(name = "DELETE_F", nullable = false, length = 1)
	@Type(type = "yes_no")
	private boolean deleteF;

}

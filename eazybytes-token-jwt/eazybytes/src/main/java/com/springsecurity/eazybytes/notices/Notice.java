package com.springsecurity.eazybytes.notices;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notice_details")
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noticeId;

	private String noticeSummary;

	private String noticeDetails;

	private Date noticBegDt;

	private Date noticEndDt;

	private Date createDt;

	private Date updateDt;

}

package com.springsecurity.eazybytes.notices;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeId;

	private String noticeSummary;

	private String noticeDetails;

	private LocalDateTime noticBegDt;

	private LocalDateTime noticEndDt;

	@CreationTimestamp
	private LocalDateTime createDt;

	@UpdateTimestamp
	private LocalDateTime updateDt;

}

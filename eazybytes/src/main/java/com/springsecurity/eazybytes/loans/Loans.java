package com.springsecurity.eazybytes.loans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loans {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;

	private Long customerId;

	private LocalDateTime startDt;

	private String loanType;

	private Integer totalLoan;

	private Integer amountPaid;

	private Integer outstandingAmount;

	@CreationTimestamp
	private LocalDateTime createDt;

}

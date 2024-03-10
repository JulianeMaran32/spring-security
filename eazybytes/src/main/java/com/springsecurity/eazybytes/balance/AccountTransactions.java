package com.springsecurity.eazybytes.balance;

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
@Table(name = "account_transactions")
public class AccountTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	private Long accountNumber;

	private Long customerId;

	private LocalDateTime transactionDt;

	private String transactionType;

	private Integer closingBalance;

	@CreationTimestamp
	private LocalDateTime createDt;

}

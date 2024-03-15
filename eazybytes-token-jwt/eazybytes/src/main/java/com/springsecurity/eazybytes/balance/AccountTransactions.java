package com.springsecurity.eazybytes.balance;

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
@Table(name = "account_transactions")
public class AccountTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String transactionId;

	private int accountNumber;

	private int customerId;

	private Date transactionDt;

	private String transactionSummary;

	private String transactionType;

	private int transactionAmt;

	private int closingBalance;

	private String createDt;

}

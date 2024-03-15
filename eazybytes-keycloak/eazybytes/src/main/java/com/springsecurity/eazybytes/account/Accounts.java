package com.springsecurity.eazybytes.account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Accounts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountNumber;

	private int customerId;

	private String accountType;

	private String branchAddress;

	private String createDt;

}

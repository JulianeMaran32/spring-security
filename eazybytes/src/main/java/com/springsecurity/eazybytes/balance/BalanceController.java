package com.springsecurity.eazybytes.balance;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;

	@GetMapping("/myBalance")
	public Optional<AccountTransactions> getBalanceDetails(@RequestParam Long id) {
		Optional<AccountTransactions> accountTransactions = accountTransactionsRepository.
				findByCustomerIdOrderByTransactionDtDesc(id);
		if (accountTransactions != null) {
			return accountTransactions;
		} else {
			return null;
		}
	}

}

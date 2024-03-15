package com.springsecurity.eazybytes.balance;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {
		List<AccountTransactions> accountTransactions = accountTransactionsRepository.
				findByCustomerIdOrderByTransactionDtDesc(id);
		if (accountTransactions != null ) {
			return accountTransactions;
		}else {
			return null;
		}
	}

}

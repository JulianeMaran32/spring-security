package com.springsecurity.eazybytes.balance;

import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
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
	private final CustomerRepository customerRepository;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
		List<Customer> customers = customerRepository.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			List<AccountTransactions> accountTransactions = accountTransactionsRepository.
					findByCustomerIdOrderByTransactionDtDesc(customers.get(0).getId());
			if (accountTransactions != null ) {
				return accountTransactions;
			}
		}
		return null;
	}

}

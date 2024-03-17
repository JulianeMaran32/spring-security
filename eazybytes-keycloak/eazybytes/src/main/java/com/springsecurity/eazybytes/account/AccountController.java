package com.springsecurity.eazybytes.account;

import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class AccountController {

	private final AccountsRepository accountsRepository;
	private final CustomerRepository customerRepository;

	@GetMapping("/myAccount")
	public Accounts getAccountDetails(@RequestParam String email) {
		List<Customer> customers = customerRepository.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			Accounts accounts = accountsRepository.findByCustomerId(customers.get(0).getId());
			if (accounts != null) {
				return accounts;
			}
		}
		return null;
	}

}

package com.springsecurity.eazybytes.login;

import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoginController {

	private final CustomerRepository customerRepository;
	public PasswordEncoder passwordEncoder;

	@RequestMapping("/user")
	public Customer getUserDetailsAfterLogin(Authentication authentication) {
		List<Customer> customers = customerRepository.findByEmail(authentication.getName());
		if (customers.size() > 0) {
			return customers.get(0);
		} else {
			return null;
		}

	}

}

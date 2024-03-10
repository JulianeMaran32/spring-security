package com.springsecurity.eazybytes.customer.controller;

import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

	private final CustomerRepository customerRepository;
	PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

		Customer savedCustomer = null;
		ResponseEntity response = null;

		try {

			String hashPwd = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			customer.setCreateDt(LocalDateTime.now());
			savedCustomer = customerRepository.save(customer);

			if (savedCustomer.getId() > 0) {
				response = ResponseEntity
						.status(HttpStatus.CREATED)
						.body("Given user details are successfully registered");
			}

		} catch (Exception ex) {
			response = ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occured due to " + ex.getMessage());
		}

		return response;

	}

	@RequestMapping("/user")
	public Customer getUserDetailsAfterLogin(Authentication authentication) {

		Optional<Customer> customers = customerRepository
				.findByEmail(authentication.getName());

		if (customers.isPresent()) {
			return customers.get();
		} else {
			return null;
		}

	}

}

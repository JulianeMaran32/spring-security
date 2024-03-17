package com.springsecurity.eazybytes.cards;

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
public class CardsController {

	private final CardsRepository cardsRepository;
	private final CustomerRepository customerRepository;

	@GetMapping("/myCards")
	public List<Cards> getCardDetails(@RequestParam String email) {
		List<Customer> customers = customerRepository.findByEmail(email);
		if (customers != null && !customers.isEmpty()) {
			List<Cards> cards = cardsRepository.findByCustomerId(customers.get(0).getId());
			if (cards != null ) {
				return cards;
			}
		}
		return null;
	}

}

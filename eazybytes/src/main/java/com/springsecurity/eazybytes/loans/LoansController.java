package com.springsecurity.eazybytes.loans;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoansController {

	private final LoanRepository loanRepository;

	@GetMapping("/myLoans")
	public Optional<Loans> getLoanDetails(@RequestParam Long id) {
		Optional<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

}

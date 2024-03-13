package com.springsecurity.eazybytes.loans;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoansController {

	private final LoanRepository loanRepository;

	@GetMapping("/myLoans")
	@PreAuthorize("hasRole('USER')")
	public List<Loans> getLoanDetails(@RequestParam int id) {
		List<Loans> loans = loanRepository
				.findByCustomerIdOrderByStartDtDesc(id);
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

}

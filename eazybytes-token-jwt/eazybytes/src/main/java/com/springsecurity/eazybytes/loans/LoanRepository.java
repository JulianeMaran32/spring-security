package com.springsecurity.eazybytes.loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Long> {

	// @PreAuthorize("hasRole('ROOT')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}



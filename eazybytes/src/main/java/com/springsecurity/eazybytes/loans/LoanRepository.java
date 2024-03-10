package com.springsecurity.eazybytes.loans;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Long> {

	Optional<Loans> findByCustomerIdOrderByStartDtDesc(Long customerId);

}



package com.springsecurity.eazybytes.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions, Long> {

	Optional<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(Long customerId);

}



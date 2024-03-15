package com.springsecurity.eazybytes.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

	Accounts findByCustomerId(int customerId);

}



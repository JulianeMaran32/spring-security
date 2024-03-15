package com.springsecurity.eazybytes.customer.repository;

import com.springsecurity.eazybytes.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByEmail(String email);

}



package com.springsecurity.eazybytes.cards;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends JpaRepository<Cards, Long> {

	List<Cards> findByCustomerId(int customerId);

}



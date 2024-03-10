package com.springsecurity.eazybytes.cards;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Cards, Long> {

	Optional<Cards> findByCustomerId(Long customerId);

}



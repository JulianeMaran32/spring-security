package com.springsecurity.eazybytes.cards;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@AllArgsConstructor
public class CardsController {

	private final CardsRepository cardsRepository;

	@GetMapping("/myCards")
	public Optional<Cards> getCardDetails(@RequestParam Long id) {
		Optional<Cards> cards = cardsRepository.findByCustomerId(id);
		if (cards != null) {
			return cards;
		} else {
			return null;
		}
	}

}

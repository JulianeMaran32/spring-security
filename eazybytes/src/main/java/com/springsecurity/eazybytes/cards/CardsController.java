package com.springsecurity.eazybytes.cards;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CardsController {

	@GetMapping("/myCards")
	public String getCardDetails() {
		return "Here are the card details from the DB";
	}

}

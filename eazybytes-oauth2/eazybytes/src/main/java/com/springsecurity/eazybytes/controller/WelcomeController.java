package com.springsecurity.eazybytes.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class WelcomeController {

	@GetMapping("/")
	public String main(OAuth2AuthenticationToken token) {
		System.out.println(token.getPrincipal());
		return "secure.html";
	}

}

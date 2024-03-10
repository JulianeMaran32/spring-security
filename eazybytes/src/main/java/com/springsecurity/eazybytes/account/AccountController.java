package com.springsecurity.eazybytes.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AccountController {

	@GetMapping("/myAccount")
	public String getAccountDetails() {
		return "Here are the account details from the DB";
	}

}

package com.springsecurity.eazybytes.notices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NoticesController {

	@GetMapping("/notices")
	public String getNotices() {
		return "Here are the notices details from the DB";
	}

}

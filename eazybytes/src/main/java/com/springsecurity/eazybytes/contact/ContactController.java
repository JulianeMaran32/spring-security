package com.springsecurity.eazybytes.contact;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping
@AllArgsConstructor
public class ContactController {

	private final ContactRepository contactRepository;

	@PostMapping("/contact")
	public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
		contact.setContactId(Long.valueOf(getServiceReqNumber()));
		contact.setCreateDt(LocalDateTime.now());
		return contactRepository.save(contact);
	}

	public String getServiceReqNumber() {
		var random = new Random();
		int ranNum = random.nextInt(999999999 - 9999) + 9999;
		return "SR"+ranNum;
	}

}

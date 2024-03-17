package com.springsecurity.eazybytes.contact;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping
@AllArgsConstructor
public class ContactController {

	private final ContactRepository contactRepository;

	@PostMapping("/contact")
	@PostFilter(value = "filterObject.contactName != 'Test'")
	public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {

		Contact contact = contacts.get(0);
		contact.setContactId(getServiceReqNumber());
		contact.setCreateDt(LocalDateTime.now());
		contact = contactRepository.save(contact);

		List<Contact> returnContacts = new ArrayList();
		returnContacts.add(contact);

		return returnContacts;
	}

	public String getServiceReqNumber() {
		var random = new Random();
		int ranNum = random.nextInt(999999999 - 9999) + 9999;
		return "SR" + ranNum;
	}

}

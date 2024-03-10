package com.springsecurity.eazybytes.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_messages")
public class Contact {

	@Id
	private String contactId;

	private String contactName;

	private String contactEmail;

	private String subject;

	private String message;

	private LocalDateTime createDt;


}

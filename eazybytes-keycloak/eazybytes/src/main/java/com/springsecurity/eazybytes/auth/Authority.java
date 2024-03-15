package com.springsecurity.eazybytes.auth;

import com.springsecurity.eazybytes.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

}

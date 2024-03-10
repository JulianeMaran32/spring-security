package com.springsecurity.eazybytes.security.service;

import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApiUserDetails implements UserDetailsService {

	private final CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String userName, password;
		List<GrantedAuthority> authorities;
		Optional<Customer> customer = customerRepository.findByEmail(username);

		if (customer.isPresent()) {
			throw new UsernameNotFoundException("User details not found for the user : " + username);
		} else {
			userName = customer.get().getEmail();
			password = customer.get().getPwd();
			authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(customer.get().getRole()));
		}

		return new User(userName, password, authorities);

	}

}

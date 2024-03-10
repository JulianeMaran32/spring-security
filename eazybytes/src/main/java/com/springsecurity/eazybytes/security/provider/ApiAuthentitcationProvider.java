package com.springsecurity.eazybytes.security.provider;

import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ApiAuthentitcationProvider implements AuthenticationProvider {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		Optional<Customer> customer = customerRepository.findByEmail(username);

		if (customer.isPresent()) {

			if (passwordEncoder.matches(pwd, customer.get().getPwd())) {
				List<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(customer.get().getRole()));
				return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
			}

			throw new BadCredentialsException("Invalid password!");

		}

		throw new BadCredentialsException("No user registered with this details!");

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}

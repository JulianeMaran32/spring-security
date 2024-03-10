package com.springsecurity.eazybytes.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApiSecurityConfig {

	/**
	 * Below is the custom security configurations
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(request -> request
						.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
						.requestMatchers("/api", "/notices", "/contact", "/welcome",
								"/customers", "/customers/register").permitAll()
				)
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();

	}

	/**
	 * NoOpPasswordEncoder is not recommended for production usage. Use only for non-prod.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * Configuration to deny all the requests
	 */
//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//		http
//				.authorizeHttpRequests(request -> request.anyRequest().denyAll())
//				.formLogin(Customizer.withDefaults())
//				.httpBasic(Customizer.withDefaults());
//		return http.build();
//
//	}


	/**
	 * Configuration to permit all the requests
	 */
//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//
//		http
//				.authorizeHttpRequests(request -> request.anyRequest().permitAll())
//				.formLogin(Customizer.withDefaults())
//				.httpBasic(Customizer.withDefaults());
//		return http.build();
//
//	}

}

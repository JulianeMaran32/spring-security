package com.springsecurity.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecOAuth2GitHubConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests)->requests.anyRequest().authenticated())
				.oauth2Login(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public ClientRegistrationRepository clientRepository() {
		ClientRegistration clientReg = clientRegistration();
		return new InMemoryClientRegistrationRepository(clientReg);
	}

	private ClientRegistration clientRegistration() {
		return CommonOAuth2Provider.GITHUB
				.getBuilder("github")
				.clientId("3d3319e60f6cab25e468")
				.clientSecret("2198da791cc3b26820d766feba49a326690c1ecd")
				.build();
	}

}

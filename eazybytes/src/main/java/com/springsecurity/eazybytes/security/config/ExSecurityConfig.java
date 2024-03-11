package com.springsecurity.eazybytes.security.config;

//@Configuration
//@EnableWebSecurity
public class ExSecurityConfig {

//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//		requestHandler.setCsrfRequestAttributeName("_csrf");
//
//		http.securityContext((context) -> context
//						.requireExplicitSave(false))
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//				.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
//					CorsConfiguration config = new CorsConfiguration();
//					config.setAllowedOrigins(Collections.singletonList("*"));
//					config.setAllowedMethods(Collections.singletonList("*"));
//					config.setAllowCredentials(true);
//					config.setAllowedHeaders(Collections.singletonList("*"));
//					config.setMaxAge(3600L);
//					return config;
//				})).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
//						.ignoringRequestMatchers("/contact", "/register")
//						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//				.authorizeHttpRequests((requests) -> requests
//						.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//						.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
//						.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//						.requestMatchers("/MyCards").hasAuthority("VIEWCARDS")
//						.requestMatchers("/user").authenticated()
//						.requestMatchers("/notices", "/contact", "/register").permitAll())
//				.formLogin(Customizer.withDefaults())
//				.httpBasic(Customizer.withDefaults());
//		return http.build();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}

package com.springsecurity.eazybytes.security.config;

import com.springsecurity.eazybytes.security.filter.csrf.CsrfCookieFilter;
import com.springsecurity.eazybytes.security.filter.loggin.AuthoritiesLoggingAfterFilter;
import com.springsecurity.eazybytes.security.filter.loggin.AuthoritiesLoggingAtFilter;
import com.springsecurity.eazybytes.security.filter.token.JWTTokenGeneratorFilter;
import com.springsecurity.eazybytes.security.filter.token.JWTTokenValidatorFilter;
import com.springsecurity.eazybytes.security.filter.validation.RequestValidationBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

/**
 * Classe de configuração de segurança para a API.
 * <p>
 * Define as configurações personalizadas de segurança para proteger o sistema contra acesso não autorizado, uso
 * indevido e outras ameaças.
 * <p>
 * `@EnableWebSecurity` habilita a segurança do Spring Security.
 * <p>
 * `@EnableWebSecurity(debug = true)` (opcional) habilita logs de debug da segurança. Não é recomendado em produção.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
@Configuration
public class ApiSecurityConfig {

	/**
	 * Define a cadeia de segurança padrão da aplicação.
	 * <p>
	 * O método configura diversas opções de segurança, incluindo:
	 * <p>
	 * * Sessão: define a política de criação de sessão como STATELESS (sem estado).
	 * <p>
	 * * CORS: define o CORS (Cross-Origin Resource Sharing) permitindo requisições de qualquer origem (*).
	 * <p>
	 * * CSRF: configura a proteção CSRF (Cross-Site Request Forgery) com as seguintes características: <br> * Token
	 * CSRF enviado no atributo "_csrf". <br> * Ignora as URLs "/contact", "/register". <br> * Armazena o token CSRF em
	 * um cookie acessível por Javascript.
	 * <p>
	 * * Filtros de segurança: adiciona diversos filtros de segurança na cadeia em uma ordem específica: <br> *
	 * CsrfCookieFilter: Adiciona o token CSRF ao cookie. <br> * RequestValidationBeforeFilter: Realiza validação de
	 * requisições antes da autenticação básica. <br> * AuthorizatiesLogginAtFilter: Registra log de autorização no
	 * início da requisição. <br> * AuthorizatiesLogginAfterFilter: Registra log de autorização no final da requisição.
	 * <br> * JWTTokenGeneratorFilter: Gera token JWT caso necessário. <br> * JWTTokenValidatorFilter: Valida o token
	 * JWT recebido na requisição.
	 * <p>
	 * * Autorização: define as autorizações de acesso para cada endpoint da API.
	 * <p>
	 * * Autenticação: configura a autenticação básica e por formulário com configurações padrão.
	 *
	 * @param http
	 * 		Objeto HttpSecurity utilizado para configurar a segurança.
	 *
	 * @return SecurityFilterChain A cadeia de segurança configurada.
	 *
	 * @throws Exception
	 * 		Lançada caso ocorra algum erro durante a configuração.
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		var requestHandler = new CsrfTokenRequestAttributeHandler();
		requestHandler.setCsrfRequestAttributeName("_csrf");

		http
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setExposedHeaders(List.of("Authorization"));
					config.setMaxAge(3600L);
					return config;
				})).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
						.ignoringRequestMatchers("/contact", "/register")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/myAccount").hasRole("USER")
						.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
//						.requestMatchers("/myLoans").hasRole("USER")
						.requestMatchers("/myLoans").authenticated()
						.requestMatchers("/myCards").hasRole("USER")
						.requestMatchers("/user").authenticated()
						.requestMatchers("/notices", "/contact", "/register").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());

		return http.build();

	}

	/**
	 * Cria e retorna um codificador de senha utilizando o algoritmo BCrypt.
	 *
	 * @return PasswordEncoder objeto utilizado para codificar senhas de usuários.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

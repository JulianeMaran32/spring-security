package com.springsecurity.eazybytes.security.filter.loggin;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Esta classe (`AuthorizatiesLogginAtFilter`) é um filtro de segurança baseado na API de Servlet do Java que registra
 * uma mensagem de informação quando a validação de autenticação está em andamento no Spring Security.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
@Slf4j
public class AuthorizatiesLogginAtFilter implements Filter {

	/**
	 * Este método é invocado pelo Spring Security durante o processamento de uma requisição.
	 * <p>
	 * Registra uma mensagem de informação (`log.info`) indicando que a validação de autenticação está em andamento.
	 * <p>
	 * Permite que a requisição continue na cadeia de filtros chamando `chain.doFilter(request, response)`.
	 *
	 * @param request
	 * 		The request to process
	 * @param response
	 * 		The response associated with the request
	 * @param chain
	 * 		Provides access to the next filter in the chain for this filter to pass the request and response to for further
	 * 		processing
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("Authentication Validation is in progress!");
		chain.doFilter(request, response);
	}

}

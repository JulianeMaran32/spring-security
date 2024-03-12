package com.springsecurity.eazybytes.security.filter.loggin;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.logging.Logger;


public class AuthoritiesLoggingAtFilter implements Filter {

	private final Logger LOG =
			Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

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
		LOG.info("Authentication Validation is in progress");
		chain.doFilter(request, response);
	}

}

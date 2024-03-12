package com.springsecurity.eazybytes.security.filter.loggin;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

/**
 * Esta classe é um filtro de segurança baseado na API de Servlet do Java que registra informações sobre usuários
 * autenticados após a execução da cadeia de filtros de segurança no Spring Security.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
@Slf4j
public class AuthorizatiesLogginAfterFilter implements Filter {

	/**
	 * Este método é invocado pelo Spring Security durante o processamento de uma requisição.
	 * <p>
	 * Obtém a informação de autenticação do contexto de segurança usando
	 * `SecurityContextHolder.getContext().getAuthentication()`.
	 * <p>
	 * Se a autenticação for bem-sucedida (ou seja, authentication não é nulo), o filtro registra uma mensagem de
	 * informação (`log.info`) contendo: <br> * O nome do usuário autenticado. <br> * As autoridades (roles) do usuário.
	 * <br>
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

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (null != authentication) {
			log.info("User {} is successfully authenticated and has the authorities {}",
					authentication.getName(), authentication.getAuthorities().toString());
		}

		chain.doFilter(request, response);

	}

}

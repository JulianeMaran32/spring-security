package com.springsecurity.eazybytes.security.filter.csrf;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Trata-se de um filtro de Segurança baseado na API de Servlet do Java que implementa proteção CSRF (Cross-Site Request
 * Forgery). Ele estende a classe `OncePerRequestFilter` do Spring Security e é executado paneas uma vez por
 * requisição.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

	/**
	 * Este método é invocado pelo Spring Security durante o processamento de uma requisição.
	 * <p>
	 * Ele recupera o token CSRF do atributo de requisição usando a chave `CsrfToken.class.getName()`
	 * <p>
	 * Se o token CSRF tiver um cabeçalho associado (`getHeaderName` não nulo), o filtro define esse cabeçalho na
	 * resposta HTTP (`response`). O valor do cabeçalho é definido como o token CSRF (`getToken`).
	 * <p>
	 * Por fim, o filtro permite que a requisição continue chamando `filterChain.doFilter(request, response)`
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if (null != csrfToken.getHeaderName()) {
			response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
		}
		filterChain.doFilter(request, response);
	}

}

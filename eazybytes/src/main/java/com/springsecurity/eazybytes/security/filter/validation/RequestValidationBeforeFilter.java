package com.springsecurity.eazybytes.security.filter.validation;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Esta classe é um filtro de segurança baseado na API de Servlet do Java que realiza validação de requisições antes da
 * execução da autenticação básica no Spring Security.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
public class RequestValidationBeforeFilter implements Filter {

	// Constante com o valor "Basic", representando o esquema de autenticação básica.
	public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";

	// Charset utilizado para decodificar credenciais de autenticação básica (padrão: UTF-8).
	private Charset credentialsCharset = StandardCharsets.UTF_8;

	/**
	 * Este método é invocado pelo Spring Security durante o processamento de uma requisição.
	 * <p>
	 * Obtém o cabeçalho de autorização (AUTHORIZATION) da requisição.
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

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String header = httpServletRequest.getHeader(AUTHORIZATION);

		if (header != null) {

			header = header.trim();

			if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {

				byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
				byte[] decoded;

				try {

					decoded = Base64.getDecoder().decode(base64Token);
					String token = new String(decoded, credentialsCharset);
					int delim = token.indexOf(":");

					if (delim == -1) {
						throw new BadCredentialsException("Invalid basic authentication token");
					}

					String email = token.substring(0, delim);
					if (email.toLowerCase().contains("test")) {
						httpServletResponse.setStatus(httpServletResponse.SC_BAD_REQUEST);
						return;
					}

				} catch (IllegalArgumentException e) {
					throw new BadCredentialsException("Failed to decode basic authentication token");
				}

			}

		}

		chain.doFilter(request, response);

	}

}

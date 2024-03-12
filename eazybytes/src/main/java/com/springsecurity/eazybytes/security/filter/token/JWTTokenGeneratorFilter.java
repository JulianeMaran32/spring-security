package com.springsecurity.eazybytes.security.filter.token;

import com.springsecurity.eazybytes.security.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Esta classe é um filtro de segurança baseado na API de Servlet do Java que gera tokens JWT (JSON Web Token) para
 * usuários autenticados no Spring Security.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	/**
	 * Este método é invocado pelo Spring Security durante o processamento de uma requisição.
	 * <p>
	 * Obtém a informação de autenticação do contexto de segurança usando
	 * `SecurityContextHolder.getContext().getAuthentication()`.
	 * <p>
	 * Se a autenticação for bem-sucedida (ou seja, authentication não é nulo): <br> * Gera uma chave secreta
	 * (`SecretKey`) a partir da constante `SecurityConstants.JWT_KEY`. <br> * Constrói um token JWT usando
	 * `Jwts.builder()`
	 * <p>
	 * Permite que a requisição continue na cadeia de filtros chamando `filterChain.doFilter(request, response)`.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (null != authentication) {

			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY
					.getBytes(StandardCharsets.UTF_8));

			String jwt = Jwts.builder().issuer("Eazy Bytes").subject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", populateAuthorities(authentication.getAuthorities()))
					.issuedAt(new Date())
					.expiration(new Date((new Date()).getTime() + 30000000))
					.signWith(key).compact();

			response.setHeader(SecurityConstants.JWT_HEADER, jwt);

		}

		filterChain.doFilter(request, response);

	}

	/**
	 * Este método determina se o filtro deve ser aplicado à requisição.
	 * <p>
	 * Retorna `true` se o caminho da requisição não for "/user", ou seja, o filtro será aplicado apenas à requisição
	 * "/user".
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/user");
	}

	/**
	 * Este método privado converte uma coleção de autoridades (`GrantedAuthority`) em uma string separada por
	 * vírgulas.
	 * <p>
	 * Ele é usado para incluir as autoridades do usuário no token JWT.
	 */
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
	}

}

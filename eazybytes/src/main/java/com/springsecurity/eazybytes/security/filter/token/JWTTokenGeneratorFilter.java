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

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

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
	 * getServletPath(): Este método retorna a parte do URI da requisição que indica o contexto da requisição. O
	 * contexto sempre aparece na primeira posição do URI. O caminho começa com um caractere "/" (barra) mas não termina
	 * com outro caractere "/". Para servlets no contexto padrão (raiz), este método retorna uma string vazia (" "). O
	 * contêiner web não decodifica esta string.
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/user");
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
		for (GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		return String.join(",", authoritiesSet);
	}

}

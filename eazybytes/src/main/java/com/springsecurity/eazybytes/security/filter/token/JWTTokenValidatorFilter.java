package com.springsecurity.eazybytes.security.filter.token;

import com.springsecurity.eazybytes.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Esta classe é um filtro de segurança baseado na API de Servlet do Java que valida tokens JWT (JSON Web Token)
 * recebidos nas requisições. O filtro estende a classe `OncePerRequestFilter` do Spring Security e é executado apenas
 * uma vez por requisição.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

	/**
	 * Este método é invocado pelo Spring Security durante o processamento de uma requisição.
	 * <p>
	 * Recupera o token JWT do cabeçalho da requisição usando o nome definido na constante
	 * SecurityConstants.JWT_HEADER.
	 * <p>
	 * Se o token JWT estiver presente: Tenta validar o token usando a biblioteca JJWT
	 * <p>
	 * Se ocorrer uma exceção durante a validação do token, lança uma exceção BadCredentialsException indicando token
	 * inválido.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);

		if (null != jwt) {

			try {
				SecretKey key = Keys.hmacShaKeyFor(
						SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

				Claims claims = Jwts.parser()
						.verifyWith(key)
						.build()
						.parseSignedClaims(jwt)
						.getPayload();

				String username = String.valueOf(claims.get("username"));
				String authorities = (String) claims.get("authorities");
				Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
						AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				throw new BadCredentialsException("Invalid Token received!");
			}

		}

		filterChain.doFilter(request, response);

	}

	/**
	 * Este método determina se o filtro deve ser aplicado à requisição.
	 * <p>
	 * Retorna true se o caminho da requisição não for "/user", ou seja, o filtro será aplicado apenas à requisição
	 * "/user".
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/user");
	}

}

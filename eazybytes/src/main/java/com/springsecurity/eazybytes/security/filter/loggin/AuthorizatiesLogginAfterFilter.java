package com.springsecurity.eazybytes.security.filter.loggin;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class AuthorizatiesLogginAfterFilter implements Filter {
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
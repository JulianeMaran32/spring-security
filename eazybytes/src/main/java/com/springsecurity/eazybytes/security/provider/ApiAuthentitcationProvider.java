package com.springsecurity.eazybytes.security.provider;

import com.springsecurity.eazybytes.auth.Authority;
import com.springsecurity.eazybytes.customer.entity.Customer;
import com.springsecurity.eazybytes.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Esta classe é uma implementação da interface `AuthenticationProvider` do Spring Security e é responsável por
 * autenticar usuários no sistema.
 * <p>
 * A classe é anotada com `@Component` para indicar que ela é um bean gerenciado pelo Spring.
 *
 * @author juliane.maran
 * @since 11-03-2024
 */
@Component
@AllArgsConstructor
public class ApiAuthentitcationProvider implements AuthenticationProvider {

	// Repositório de dados para acesso a informações de usuários (clientes).
	private final CustomerRepository customerRepository;

	// Bean responsável por codificar e comparar senhas de forma segura.
	private final PasswordEncoder passwordEncoder;

	/**
	 * Este método é invocado pelo Spring Security para realizar a autenticação de um usuário.
	 * <p>
	 * Ele recebe um objeto Authentication que contém as credenciais do usuário (nome de usuário e senha).
	 * <p>
	 * O método executa as seguintes etapas:
	 * <p>
	 * * Recupera o nome de usuário e a senha da credencial de autenticação.
	 * <p>
	 * * Busca o usuário no repositório (`customerRepository.findByEmail(username)`) usando o e-mail fornecido.
	 * <p>
	 * * Se o usuário for encontrado: <br> * Compara a senha fornecida com a senha armazenada no banco de dados usando o
	 * codificador de senha (`passwordEncoder.matches`). <br> * Se as senhas corresponderem, cria e retorna um novo
	 * objeto `UsernamePasswordAuthenticationToken` contendo o nome de usuário, senha e as autoridades do usuário. <br>
	 * * Se as senhas não corresponderem, lança uma exceção `BadCredentialsException` indicando senha inválida. <br>
	 * <p>
	 * * Se o usuário não for encontrado, lança uma exceção `BadCredentialsException` indicando que não há usuário
	 * registrado com as credenciais fornecidas.
	 *
	 * @param authentication
	 * 		the authentication request object.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		Optional<Customer> customer = customerRepository.findByEmail(username);

		if (customer.isPresent()) {

			if (passwordEncoder.matches(pwd, customer.get().getPwd())) {
				return new UsernamePasswordAuthenticationToken(username, pwd,
						getGrantedAuthorities(customer.get().getAuthorities()));
			} else {
				throw new BadCredentialsException("Invalid password!");
			}

		} else {
			throw new BadCredentialsException("No user registered with this details!");
		}

	}

	/**
	 * Este método privado converte um conjunto de autoridades (`Set<Authority>`) do usuário para uma lista de objetos
	 * `GrantedAuthority` compatível com o Spring Security.
	 * <p>
	 * Ele itera sobre o conjunto de autoridades e adiciona um novo objeto `SimpleGrantedAuthority` para cada autoridade
	 * na lista de retorno.
	 */
	private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Authority authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
		}
		return grantedAuthorities;
	}

	/**
	 * Este método indica se esse provedor de autenticação suporta o tipo de autenticação fornecido.
	 * <p>
	 * Ele retorna `true` se a classe de autenticação for compatível com `UsernamePasswordAuthenticationToken` e `false`
	 * caso contrário.
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}

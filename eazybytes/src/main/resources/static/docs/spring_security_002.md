# Changing the defulat security configurations

A partir das versões **Spring Security 6.1** e **Spring Boot 3.1.0**, a equipe do framework Spring Security recomenda o
uso do estilo Lambda DSL para configurar a segurança de APIs, caminhos da web etc. Consequentemente, alguns métodos do
framework foram marcados como obsoletos. Esses métodos obsoletos devem ser removidos no Spring Security 7, cuja previsão
de lançamento é para os próximos 2-3 anos. Esse prazo permite tempo suficiente para todos os desenvolvedores migrarem
seu código.

No entanto, não há necessidade de se preocupar, pois essa mudança não altera os conceitos subjacentes. Em vez de usar
configurações Java normais, agora empregaremos o Lambda DSL. Abaixo, você encontrará um exemplo de código ilustrando as
diferenças entre os dois estilos:

### Sem Lambda DSL

```java

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests()
				.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
				.requestMatchers("/notices", "/contact").permitAll()
				.and().formLogin()
				.and().httpBasic();
		return http.build();

	}

}
```

### Com Lambda DSL

```java

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
						.requestMatchers("/notices", "/contact").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();

	}

}
```

Observe que o curso foi gravado usando as versões Spring Boot 3 e Spring Security 6, onde as configurações normais do
estilo Java são usadas em vez do Lambda DSL. Portanto, eu recomendo que você siga os vídeos para entender os conceitos,
métodos, configurações etc. e tente usar configurações do estilo Lambda DSL.

Para sua referência, atualizei o código no repositório GitHub para usar o estilo Lambda DSL. Você deve ser capaz de
compreender o estilo Lambda DSL, mas se tiver alguma dúvida, não hesite em entrar em contato comigo.

### Objetivos do Lambda DSL

* Indentação automática torna a configuração mais legível.
* Não há necessidade de encadear opções de configuração usando `.and()`.
* O Spring Security DSL possui um estilo de configuração semelhante a outros DSLs do Spring, como Spring Integration e
  Spring Cloud Gateway.

### Configurando Usuários

Observação: Não é recomendado utilizar em produção.

Ao invés de definir um único usuário dentro do application.yaml, como próximo passo, podemos definir vários usuários
junto com suas autorizações com a ajuda do `InMemoryUserDetailsManager` e `UserDetails`.

* A Abordagem 1 usa o método `withDefaultPasswordEncoder()` para criar os detalhes do usuário.

```java

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

	/**
	 * Approach 1 where we use `withDefaultPasswordEncoder()` method while creating the user details.
	 * InMemoryUserDetailsManager: Gerador de usuários em memória
	 * UserDetails: detalhes do usuário
	 * withDefaultPasswordEncoder(): com codificador de senha padrão
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {

		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("admin") // nome do usuário
				.password("12345") // senha
				.authorities("admin") // authoridades (permissões do usuário)
				.build(); // constrói o objeto UserDetails

		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user") // nome do usuário
				.password("12345") // senha
				.authorities("read") // permissão leitura
				.build(); // constrói o objeto UserDetails

		return new InMemoryUserDetailsManager(admin, user);

	}

}
```

* A Abordagem 2 utiliza o `NoOpPasswordEncoder` `Bean` ao criar os detalhes do usuário.

**Observação**: É importante ressaltar que o `NoOpPasswordEncoder` NÃO É SEGURO para ambientes de produção. Ele armazena
as senhas como plain text, o que as torna vulneráveis a ataques. Use esta abordagem apenas para fins de desenvolvimento
ou demonstração.

```java

@Configuration
public class SecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {

		UserDetails admin = User.withUsername("admin") // nome do usuário
				.password("12345") // senha
				.authorities("admin") // authoridades (permissões do usuário)
				.build(); // constrói o objeto UserDetails

		UserDetails user = User.withUsername("user") // nome do usuário
				.password("12345") // senha
				.authorities("read") // permissão leitura
				.build(); // constrói o objeto UserDetails

		return new InMemoryUserDetailsManager(admin, user);

	}

	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
```
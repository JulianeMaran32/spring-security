# Username/Password Authentication (Autenticação por Nome de Usuário e Senha)

Uma das maneiras mais comuns de autenticar um usuário é validando seu nome de usuário e senha. O Spring Security oferece
suporte abrangente para autenticação com nome de usuário e senha.

Você pode configurar a autenticação por nome de usuário e senha usando o seguinte:

* Exemplo Simples de Nome de Usuário/Senha

```java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.anyRequest().authenticated()
				)
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

}
```

A configuração anterior registra automaticamente um `UserDetailsService` in-memory com o `SecurityFilterChain`, registra
o `DaoAuthenticationProvider` com o `AuthenticationManager` padrão e habilita o Login por Formulário e a autenticação
HTTP Basic.

## Publicação de um bean `AuthenticationManager` (Publicar um bean Gerenciador de Autenticação)

Uma necessidade bastante comum é publicar um bean `AuthenticationManager` ara permitir a autenticação personalizada,
como em um serviço `@Service` ou controlador Spring MVC `@Controller`. Por exemplo, você pode querer autenticar usuários
por meio de uma API REST em vez de usar o Login por Formulário.

Você pode publicar um `AuthenticationManager` para cenários de autenticação personalizados usando a seguinte
configuração:

* Publicar bean `AuthenticationManager` para Autenticação Personalizada

```java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/login").permitAll()
						.anyRequest().authenticated()
				);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
```

Com a configuração anterior em vigor, você pode criar um `@RestController` que usa o `AuthenticationManager` da seguinte
maneira:

* Criar um `@RestController` para Autenticação

```java

@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest =
				UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse =
				this.authenticationManager.authenticate(authenticationRequest);
		// ...
	}

	public record LoginRequest(String username, String password) {
	}

}
```

**Observação**: Neste exemplo, é sua responsabilidade salvar o usuário autenticado no `SecurityContextRepository` se
necessário. Por exemplo, se estiver usando o `HttpSession` para persistir o `SecurityContext` entre requisições, você
pode usar `HttpSessionSecurityContextRepository`.

## Personalizar o `AuthenticationManager` (Gerenciador de Autenticação Personalizado)

Normalmente, o Spring Security cria um `AuthenticationManager` internamente composto por um `DaoAuthenticationProvider`
para autenticação com nome de usuário/senha. Em certos casos, ainda pode ser desejável personalizar a instância
de `AuthenticationManager` usada pelo Spring Security. Por exemplo, você pode precisar simplesmente desabilitar o
apagamento de credenciais para usuários em cache.

A maneira recomendada de fazer isso é simplesmente publicar seu próprio bean `AuthenticationManager`, e o Spring
Security o usará. Você pode publicar um `AuthenticationManager` usando a seguinte configuração:

* Publicar bean `AuthenticationManager` para o Spring Security

```java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/login").permitAll()
						.anyRequest().authenticated()
				)
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);

		return providerManager;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
```

Como alternativa, você pode aproveitar o fato de que o `AuthenticationManagerBuilder` usado para construir
o `AuthenticationManager` global do Spring Security ser publicado como um bean. Você pode configurar o construtor da
seguinte maneira:

* Configurar o `AuthenticationManagerBuilder` global

```java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// ...
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		// Return a UserDetailsService that caches users
		// ...
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder builder) {
		builder.eraseCredentials(false);
	}

}
```

---

### Referências

* [Username/Password Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#servlet-authentication-unpwd-input)

Para saber mais sobre autenticação por nome de usuário/senha, considere os seguintes casos de uso:

* Quero aprender como funciona:
    * [o Login por Formulário](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html)
    * [a autenticação HTTP Basic](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html)
    * [o `DaoAuthenticationProvider`](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html)
* Quero gerenciar usuários:
    * [em memória](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html)
    * [em um banco de dados](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html)
    * [em LDAP](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/ldap.html#servlet-authentication-ldap-authentication)
* [Quero publicar um bean `AuthenticationManager` para autenticação personalizada](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#publish-authentication-manager-bean)
* [Quero personalizar o `AuthenticationManager` global](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#customize-global-authentication-manager)
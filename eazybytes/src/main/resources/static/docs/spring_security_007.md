## Authentication & Authorization

**Detalhes e comparação**

* Authentication (Autenticação)
    * Na autenticação, a identidade dos usuários é verificada para fornecer acesso ao sistema.
    * A autenticação (AuthN) é feita antes da autorização.
    * Geralmente, ela precisa dos dados de login do usuário.
    * Se a autenticação falhar, normalmente receberemos um erro de resposta 401.
    * Por exemplo, como cliente/funcionário de um banco, para realizar ações no aplicativo, precisamos provar nossa
      identidade.

* Authrorization (Autorização)
    * Na autorização, as permissões da pessoa ou usuário são verificadas para acessar os recursos.
    * A autorização (AuthZ) sempre acontece após a autenticação.
    * Ela precisa dos privilégios ou roles do usuário.
    * Se a autorização falhar, normalmente receberemos um erro de resposta 403.
    * Uma vez logado no aplicativo, meus roles e permissões determinarão quais tipos de ações eu posso realizar.

### How Authorities stored?

Inside Spring Security

* As informações de permissões/roles no Spring Security são armazenadas dentro de `GrantedAuthority`. Existe apenas um
  método dentro de `GrantedAuthority` que retorna o nome da permissão ou role.
* `SimpleGrantedAuthority` é a classe de implementação padrão da interface `GrantedAuthority` dentro do Spring Security
  Framework.

```java
public interface GrantedAuthority {
	String getAuthority();
}

public final class SimpleGrantedAuthority implements GrantedAuthority {

	private final String role;

	public SimpleGrantedAuthority(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

}
```

Como as informações de permissões são armazenadas dentro dos objetos das interfaces UserDetails e Authentication, que
desempenham um papel vital durante a autenticação do usuário?

* `Authentication` (interface) -> `UserPasswordAuthenticationToken` (class) -> `getAuthorities`()
* `UserDetails` (interface) -> `User` (class) -> `getAuthorities`()

### Configurando permissões

* `hasAuthority()`: aceita uma única permissão para a qual o endpoint será configurado e o usuário será validado em
  relação à permissão mencionada. Somente usuários com a mesma permissão configurada podem invocar o endpoint.

* `hasAnyAuthority()`: aceita várias permissões para as quais o endpoint será configurado e o usuário será validado em
  relação às permissões mencionadas. Somente usuários com qualquer uma das permissões configuradas podem invocar o
  endpoint.

* `access()`: Usando a Spring Expression Language (SpEL), ele oferece possibilidades ilimitadas para configurar
  permissões que não são possíveis com os métodos acima. Podemos usar operadores como OR, AND dentro do
  método `access()`.

Como mostrado abaixo, podemos configurar os requisitos de permissão para as APIs/caminhos.

```java
public class ExSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		requestHandler.setCsrfRequestAttributeName("_csrf");

		http.securityContext((context) -> context
						.requireExplicitSave(false))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
				.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("*"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setMaxAge(3600L);
					return config;
				})).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
						.ignoringRequestMatchers("/contact", "/register")
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
						.requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
						.requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
						.requestMatchers("/MyCards").hasAuthority("VIEWCARDS")
						.requestMatchers("/user").authenticated()
						.requestMatchers("/notices", "/contact", "/register").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
```
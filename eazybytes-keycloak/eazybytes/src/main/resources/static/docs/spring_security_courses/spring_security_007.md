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

### Authority vs Role

* **Authority** (Autoridade / Permissão)
    * Uma permissão é como um privilégio individual ou uma ação.
    * Restringe o acesso de maneira granular.
    * Ex.: `VIEWACCOUNT`, `VIEWCARDS` etc.

* **Role** (Papel / função)
    * Papel é um grupo de privilégios/ações.
    * Restringe o acesso de maneira ampla.
    * Ex.: `ROLE_ADMIN`, `ROLSE_USER`

* Os nomes das permissões/papéis são arbitrários e podem ser personalizados de acordo com a necessidade do negócio.
* Funções também são representados usando o mesmo contrato `GrantedAuthority` no Spring Security.
* Ao definir uma função, seu nome deve começar com o prefixo `ROLE_`. Este prefixo especifica a diferença entre um papel
  e uma autoridade.

### Configurando Permissões (Authority)

Dentro do Spring Security, os requisitos de ROLE podem ser configurados das seguintes maneiras:

* `hasRole()`: aceita um único nome de role para o qual os endpoints serão configurados e o usuário será validado em
  relação ao único role mencionado. Somente usuários com o mesmo role configurado podem invocar o endpoint.
* `hasAnyRole()`: aceita multiplos roles para os quais os endpoints serão configurados e o usuário será validado em
  relação aos roles mencionados. Somente usuários com qualquer um dos roles configurados podem chamar o endpoint.
* `access()`: usando a Spring Expression Language (SpEL), ele oferece possibilidades ilimitadas para configurar roles, o
  que não é possível com os métodos acima. Podemos usar operadores como OR, AND dentro do método `access()`.

**Observações:**

* O prefixo `ROLE_` deve ser usado somente ao configurar o role no banco de dados. Mas quando configuramos os roles,
  fazemos isso apenas pelo seu nome.
* O método `access()` pode ser usado não apenas para configurar a autorização com base em permissão ou role, mas também
  com qualquer requisito especial que tenhamos. Por exemplo, podemos configurar o acesso com base no país do usuário ou
  na hora/data atual.

### Configurando Funções / Papéis (Roles)

inside Spring Security

Como mostrado abaixo, podemos configurar os requisitos de `ROLE` para as APIs/caminhos.

```java
public class ApiSecurityConfig {

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
						.requestMatchers("/myAccount").hasRole("USER")
						.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/myLoans").hasRole("USER")
						.requestMatchers("/myCards").hasRole("USER")
						.requestMatchers("/user").authenticated()
						.requestMatchers("/notices", "/contact", "/register").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
```

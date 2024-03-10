## Entendendo CORs & CSRF

* CORS: Cross-Origin Resource Sharing
* CSRF: Cross-Site Request Forgery
* Spring Security: How to handle them using the spring security framework?

### Cross-Origin Resource Sharing (CORS)

Compartilhamento de Recursos entre Origens (CORS)

CORS é um protocolo que permite que scripts executados em um navegador cliente interajam com recursos de uma origem
diferente. Por exemplo, se um aplicativo de interface do usuário deseja fazer uma chamada de API em execução em um
domínio diferente, ele seria bloqueado por padrão devido ao CORS. É uma especificação do W3C implementada pela maioria
dos navegadores.

Portanto, CORS não é um problema/ataque de segurança, mas a proteção padrão fornecida pelos navegadores para impedir o
compartilhamento de dados/comunicação entre diferentes origens.

"Origens diferentes" significa que o URL acessado difere do local de execução do JavaScript, por ter:

* um esquema diferente (HTTP or HTTPS)
* um domínio diferente
* uma porta diferente

Por padrão, o navegador bloqueará essa comunicação devido ao CORS.

* Aplicativo de interface do usuário em execução em https://domain1.com
* API de back-end em execução em https://domain2.com

![Cross-Origin Resource Sharing](./img/spring_security_cors_001.png)

### Solução para lidar com CORS

Se temos um cenário válido, onde uma interface do usuário de um aplicativo web implantada em um servidor está tentando
se comunicar com um serviço REST implantado em outro servidor, podemos permitir esse tipo de comunicação com a ajuda da
anotação `@CrossOrigin`. `@CrossOrigin` permite que clientes de qualquer domínio consumam a API.

A anotação `@CrossOrigin` pode ser mencionada no topo de uma classe ou método, como mencionado abaixo:

* `@CrossOrigin(origins = "http://localhost:4200")` // Permitirá no domínio especificado
* `@CrossOrigin(origins = "*")` // Permitirá em qualquer domínio

* **Depois que o CORS é habilitado no back-end:**
    * Aplicativo de interface do usuário em execução em https://domain1.com
    * API de back-end em execução em https://domain2.com

![Solution to handle CORS](./img/spring_security_cors_002.png)

Em vez de mencionar a anotação `@CrossOrigin` em todos os controladores dentro do nosso aplicativo web, podemos definir
configurações relacionadas ao CORS globalmente usando o Spring Security, conforme mostrado abaixo:

```java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(request -> {
					var config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setMaxAge(3600L);
					return config;
				}))
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/users/**", "/services/**").authenticated()
						.requestMatchers("/api/petshop/**", "/customers/register").permitAll())
				.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
				.sessionManagement(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}    
```
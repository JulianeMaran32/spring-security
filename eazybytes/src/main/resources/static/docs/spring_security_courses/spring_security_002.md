## Serviços REST Backend para Aplicativo EazyBank

### Serviços sem nenhuma segurança

* `/contact` (contato): este serviço deve aceitar os detalhes do formulário da página "Entre em Contato" na interface do
  usuário e salvá-los no banco de dados.
* `/notices` (avisos): este serviço deve enviar os detalhes do aviso do banco de dados para a página "AVISOS" na
  interface do usuário.

### Serviços com segurança

* `/myAccount` (minha conta):  este serviço deve enviar os detalhes da conta do usuário logado do banco de dados para a
  interface do usuário.
* `/myBalance` (meu saldo): este serviço deve enviar o saldo e os detalhes da transação do usuário logado do banco de
  dados para a interface do usuário.
* `/myLoans` (meus empréstimos): este serviço deve enviar os detalhes do empréstimo do usuário logado do banco de dados
  para a interface do usuário.
* `/myCards` (meus cartões): este serviço deve enviar os detalhes do cartão do usuário logado do banco de dados para a
  interface do usuário.

### Configurações de Segurança Padrão

Dentro do Framework Spring Security

Por padrão, o Spring Security Framework protege todos os caminhos presentes dentro do aplicativo web. Esse comportamento
é devido ao código presente dentro do método `defaultSecurityFilterChain(HttpSecurity http)` da
classe `SpringBootWebSecurityConfiguration`.

```java
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public class SecurityConfig {

	@Bean
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers().authenticated())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```

---

NOTA IMPORTANTE:

Por padrão, o **Spring Security Framework** protege todos os caminhos presentes dentro da aplicação web. Esse
comportamento é devido ao código presente dentro do método `defaultSecurityFilterChain(HttpSecurity http)` da
classe `SpringBootWebSecurityConfiguration`.

```yaml
spring:
  security:
    user:
      name: admin
      password: 12345678
```

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

---

### Custom Security Configuration

Podemos proteger as APIs e caminhos da aplicação web de acordo com nossos requisitos personalizados usando o framework
Spring Security, como mostrado abaixo:

```java

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
						.requestMatchers("/notices", "/contact").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```

### Negar tudo

Podemos **negar** todas as solicitações que chegam às APIs e caminhos da sua aplicação web usando o framework Spring
Security, como mostrado abaixo:

```java

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers().denyAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```

### Permitir tudo

É possível permitir todas as solicitações que chegam às APIs e caminhos da sua aplicação web usando o Spring Security,
mas é importante ressaltar que isso não é recomendado para a maioria dos cenários.

```java

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.requestMatchers().permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```



## Tests

### 1. Iniciando com Spring Security

1. Requisitos relacionados à segurança para um aplicativo são mencionados em:  
   a. Requisitos funcionais  
   b. Requisitos não funcionais (correta)  
   **Comentário**: Requisitos não funcionais terão requisitos definidos em torno de segurança, desempenho,
   escalabilidade, disponibilidade etc.
2. Qual das seguintes afirmações é VERDADEIRA?  
   a. O único objetivo de implementar Segurança dentro de um aplicativo web é proteger seus dados.  
   b. Os objetivos de implementar Segurança dentro de um aplicativo web são proteger seus dados, lógica de negócios e
   salvá-lo de violações de segurança, ataques etc. (correta)  
   **Comentário**: A motivação para implementar Segurança dentro de um aplicativo web é proteger seus dados, lógica de
   negócios e evitar violações de segurança, ataques, etc.
3. Qual das opções a seguir é a melhor prática durante o desenvolvimento de aplicativos em termos de implementação de
   segurança?  
   a. Considerar a segurança desde a fase de desenvolvimento, juntamente com a lógica de negócios. (correta)  
   b. Considerar a segurança a partir da fase de teste, como UAT.  
   c. Considerar a segurança após a ocorrência de um ataque ou violação de segurança.  
   **Comentários**: A segurança deve ser considerada desde a fase de desenvolvimento, juntamente com a lógica de
   negócios. Isso evita retrabalho.
4. Qual é o projeto inicial correto do Spring Boot que precisamos adicionar para implementar segurança dentro de um
   projeto Spring Boot?  
   a. spring-boot-starter-web  
   b. spring-boot-starter-security (correto)  
   c. spring-boot-security  
   **Comentários**: Para segurança, precisamos adicionar 'spring-boot-starter-security' nas configurações do Maven da
   nossa aplicação.
5. Qual é o cookie padrão gerado pelo framework Spring Security quando ocorre uma tentativa de login, o que nos ajuda a
   fazer várias solicitações sem precisar de credenciais toda vez?  
   a. JSESSIONID (correta)  
   b. AUTHID  
   c. SESSIONID  
   **Comentário**: Por padrão, o Spring Security gera o cookie JSESSIONID durante o processo de autenticação.
6. Qual interface precisamos implementar para definir a lógica de como um usuário deve ser autenticado dentro do
   framework Spring Security?  
   a. `org.springframework.security.authentication.AuthenticationProvider` (cooreta)  
   b. `org.springframework.security.core.context.SecurityContext`  
   c. `org.springframework.security.crypto.password.PasswordEncoder`  
   **Comentário**: Você já havia acertado a explicação sobre a interface `AuthenticationProvider` anteriormente. Ela é
   usada para definir a lógica de como um usuário deve ser autenticado no Spring Security.
7. Qual dos seguintes filtros do Spring Security extrai o nome de usuário, a senha do usuário final e tenta a
   autenticação?  
   a. `AuthorizationFilter`  
   b. `UsernamePasswordAuthenticationFilter` (correta)  
   c. `DefaultLoginPageGeneratingFilter`

---

### 2. Understanding & Changing the default security configurations

1. Qual é o bean que precisamos criar para definir nossos requisitos de segurança personalizados dentro de uma aplicação
   web?   
   a. `SecurityChain`  
   b. `SecurityFilterChain` (correto)
2. Qual das seguintes opções é o código correto para criar o bean `SecurityFilterChain` para definir configurações de
   segurança personalizadas?    
   a. Código correto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

	}

}
```

b. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain() throws Exception {

	}

}
```

c. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

	}

}
```

3. Qual é o comportamento ou configuração padrão que o Spring Security segue para os endpoints de nossa aplicação?      
   a. Negar todas as solicitações, independentemente da aprovação ou falha da autenticação, usando a configuração
   abaixo:

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().denyAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

b. Permitir todas as solicitações, independentemente da aprovação ou falha da autenticação, usando a configuração
abaixo:

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().permitAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

c. Proteger todos os endpoints no aplicativo para que somente usuários autenticados possam invocar as URLs com a
configuração abaixo: (correto)

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().authenticated();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

4. Se eu tenho o seguinte requisito para proteger os endpoints da minha aplicação, qual das seguintes configurações está
   correta?
    * **"/dashboard" - Protegido**
    * **"/myProfile" - Protegido**
    * **"/home" - Não Protegido**

a. Código correto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/dashboard").authenticated()
						.requestMatchers("/myProfile").authenticated()
						.requestMatchers("/home").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```

b. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/dashboard").authenticated()
						.requestMatchers("/myProfile").authenticated()
						.requestMatchers("/home").noAuthenticated())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```

c. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/dashboard").retricted()
						.requestMatchers("/myProfile").retricted()
						.requestMatchers("/home").permitAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
```

5. Qual das seguintes configurações está correta para não permitir que ninguém acesse os endpoints, independentemente de
   ter sido autenticado com sucesso ou não?

a. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().restrictAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

b. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().deniedAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

c. Código correto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().denyAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

6. Qual das seguintes configurações está correta para permitir que qualquer pessoa acesse os endpoints,
   independentemente de ter sido autenticada com sucesso ou não?

a. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().allowAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

b. Código correto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().permitAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

c. Código incorreto

```java

@Configuration
public class ApiSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().grantAll();
		http.formLogin();
		http.httpBasic();
		return http.build();
	}

}
```

---

### 3. "Defining & Managing Users" in Spring Security

1. Qual das seguintes opções é um código válido para criar e configurar usuários dentro do Spring Security
   usando `In-Memory Authentication`?

a. Código correto

```java

import org.springframework.security.core.userdetails.User;

@Configuration
public class ApiSecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {

		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("admin")
				.password("12345")
				.authorities("admin")
				.build();

		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("12345")
				.authorities("read")
				.build();

		return new InMemoryUserDetailsManager(admin, user);

	}

}
```

b. Código incorreto

```java

import org.springframework.security.core.userdetails.User;

@Configuration
public class ApiSecurityConfig {

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {

		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("admin")
				.password("12345")
				.authorities("admin")
				.build();

		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("12345")
				.authorities("read")
				.build();

		return new InMemoryUserDetailsManager();

	}

}
```

2. Qual das seguintes interfaces representa a informação central do usuário dentro do framework Spring Security?  
   a. `org.springframework.security.core.userdetails.UserDetailsService`  
   b. `org.springframework.security.provisioning.UserDetailsManager`  
   c. `org.springframework.security.core.userdetails.UserDetails` (correta)  
3. Qual das seguintes afirmações sobre a interface `UserDetailsService` é verdadeira?  
   a. A interface `UserDetailsService` possui um único método chamado loadUserByUsername(String username), que retorna
   um objeto UserDetails. Essa interface é útil em cenários onde apenas queremos carregar dados específicos do usuário,
   sem realizar operações de create/update/delete (correta)  
   b. A interface `UserDetailsService` não suporta métodos para load/create/update/delete detalhes do usuário.  
   Ela é focada apenas na leitura de dados do usuário.  
4. Qual das seguintes afirmações sobre a classe `User` presente no framework Spring Security é verdadeira?  
   a. A classe `User` possui métodos que podem ajudar a load/create/update/delete dados relacionados ao usuário dentro
   do framework Spring Security.  
   b. `User` é uma classe de implementação de exemplo da interface UserDetails fornecida pela equipe Spring Security. O
   objeto da classe `User` ajuda a armazenar os detalhes do usuário final. (correta)  
5. Em uma aplicação, se temos o requisito de permitir criação, exclusão, atualização, seleção, alteração de senha etc.,
   qual das seguintes interfaces precisamos implementar?  
   a. `org.springframework.security.core.userdetails.UserDetailsService`  
   b. `org.springframework.security.core.userdetails.UserDetails`  
   c. `org.springframework.security.provisioning.UserDetailsManager` (correta)  
6. Qual das seguintes opções NÃO é uma implementação de `UserDetailsManager` fornecida pela equipe Spring Security?  
   a. `InMemoryUserDetailsManager`  
   b. `JdbcUserDetailsManager`  
   c. `NoSQLUserDetailsManager` (incorreta - não existe)  
   d. `LdapUserDetails Manager`  

---

### 4. Password Management in Spring Security

---

### 5. 

---

### 6.

---

### 7.

---

### 8.

---
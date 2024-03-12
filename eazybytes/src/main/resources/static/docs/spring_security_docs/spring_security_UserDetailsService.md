# `UserDetailsService` (Serviço de Detalhes do Usuário)

`UserDetailsService`  é usado por `DaoAuthenticationProvider` para recuperar o nome de usuário, senha e outros atributos
para autenticação com nome de usuário e senha. O Spring Security fornece implementações in-memory e JDBC
de `UserDetailsService`.

Você pode definir a autenticação personalizada expondo um `UserDetailsService` personalizado como um bean. Por exemplo,
a listagem a seguir personaliza a autenticação, assumindo que `CustomUserDetailsService`
implementa `UserDetailsService`:

**Observação**: Isso só é usado se o `AuthenticationManagerBuilder` não tiver sido preenchido e
nenhum `AuthenticationProviderBean` estiver definido.

* Bean `UserDetailsService` Personalizado

```java
@Bean
CustomUserDetailsService customUserDetailsService(){
		return new CustomUserDetailsService();
		}
```

---

### Referência

- [UserDetailsService](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html#servlet-authentication-userdetailsservice)
- [in-memory](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html#servlet-authentication-inmemory)
- [JDBC](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html#servlet-authentication-jdbc)
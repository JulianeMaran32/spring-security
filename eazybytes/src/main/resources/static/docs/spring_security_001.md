# Spring Security 6.1 & Spring Boot 3.1.0

## INTRODUÇÃO

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

### Why Spring Security?

### Servlets & Filters

### Spring Security internal flow


### Sequence Flow - Spring Security defualt behaviour


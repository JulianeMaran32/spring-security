
## Segurança da API

* Implementação de Autenticação Básica

Autenticação básica envolve o usuário enviando seu nome de usuário e senha sempre que precisarem invocar uma operação.
Porém, este método de autenticação não é seguro e nem recomendado, raramente é utilizado no dia a dia de um projeto
real.

    * Exemplo: `Authorization: Basic juliane:juliane`
        * Resultado: `anVsaWFuZTpqdWxpYW5l`
    * O usuário enviará seu nome de usuário e senha em cada chamada.
    * Exemplo:

      ```
      Content-Type: application/json
      charset: utf-8
      Authorization: Basic username:password

      {
          "id": "5d2ef5f7-8d4e-426e-bbd1-2364423e5368",
          "name": "Juliane Maran",
          "phoneNumber": "5511998765432"
      }
      ```

* Conexão com o Banco de Dados MySQL no Workbook 10.1

Autenticação usando Token JWT é muito mais seguro porque o payload não contém nenhum dado sensível e sua assinatura
digital é codificada de tal forma que é quase impossível de decodificar.

* Implementação de Autenticação baseada em Token

    * O cliente se autenticará usando um Token JWT
    * Exemplo:

      ```
      Content-Type: application/json
      charset: utf-8
      Authorization: Bearer JWT Token

      {
          "id": "5d2ef5f7-8d4e-426e-bbd1-2364423e5368",
          "name": "Juliane Maran",
          "phoneNumber": "5511998765432"
      }
      ```

## Authentication & Authorization

A autenticação básica envolve duas etapas principais

### Authentication

* Verifica a identidade de um cliente
* Requer que a requisição HTTP contenha no cabeçaho:

```
Authorization: Basic username:password
```

### Authorization

* Determina o que o cliente tem acesso.
* Spring Security valida a identidade do cliente.
* Spring Security verifica se o cliente tem permissão para a operação.

### Processo

1. A API exige autenticação antes de processar uma requisição.

    * Exemplo: REQUEST POST

    ```
    Authorization: Basic username:password
    {
        "id": "5d2ef5f7-8d4e-426e-bbd1-2364423e5368",
        "name": "Juliane Maran",
        "phoneNumber": "5511998765432"
    }
    ```

2. A requisição HTTP do cliente deve incluir credenciais no cabeçalho:

    ```
    Authorization: Basic username:password
    ```

3. Autenticação

* Spring Security valida a identidade do cliente.
* Se as credenciais estiverem incorretas, retorna um código de erro 401 (Não Autorizado / UNAUTHORIZED).

4. Autorização

* Spring Security determina se o cliente tem permissão para a operação.
* Se o cliente não tiver permissão, retorna um código de erro 403 (Proibido / FORBIDDEN).

---

## Autenticação básica com Spring Security

A Autenticação Básica envolve dois passos principais: autenticação e autorização. A autenticação valida a identidade do
cliente, e após o cliente ser autenticado, a autorização determina se o cliente deve ter acesso concedido.

### Iniciando

1. Adicionando Dependência de Segurança Spring Boot:

* Adicione a dependência spring-boot-starter-security ao seu projeto.
* Para VS Code:

```rust
Adicionar Starters -> Spring Boot Security Starter
```

* Para IntelliJ:

```rust
Maven -> Repositório Maven Central -> spring-boot-starter-security
```

2. Configuração Inicial

* Adicionando a classe SecurityConfig em src/main/java/security/.
* Definindo o filtro de segurança para exigir autenticação em todas as requisições.

### Definindo Regras de Segurança

* Operação DELETE:
    * Requer autenticação.
    * Apenas administradores têm acesso.

* Operação POST:
    * Requer autenticação.
    * Usuários e administradores têm acesso.

* Operações GET:
    * Acesso aberto para todos, sem autenticação.

### Criando usuários

1. Configurando UserDetailsService para Autenticação:

* Criando usuário admin com senha criptografada.
* Criando usuário user com senha criptografada.

```java
@Bean
public UserDetailsService users(){
		UserDetails admin=User.builder()
		.username("admin")
		.password(passwordEncoder.encode("adminpass"))
		.roles("ADMIN")
		.build();

		UserDetails user=User.builder()
		.username("user")
		.password(passwordEncoder.encode("userpass"))
		.roles("USER")
		.build();

		return new InMemoryUserDetailsManager(admin,user);
		}
```

### Habilitando Autenticação Básica

* Adicionando Autenticação Básica ao Header das Requisições:
    * Usuário: admin
    * Senha: adminpass

* Acesso via Postman:
    * GET para listar contatos: Requer autenticação.
    * POST para adicionar contato: Requer autenticação.
    * DELETE para remover contato: Requer autenticação de admin.

### Configuração de Estado Sem Sessão (STATELESS)

* Garantindo Estado Sem Sessão:
    * Adicionando `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` ao SecurityConfig.
    * Isso desabilita a criação de sessões pelo Spring Security, garantindo autenticação em cada requisição.

---

## Dicas

Para o exemplo acima foi utilizado um gerador online de UUID e o console do browser para gerar a senha

* UUID (Universally Unique Identifier): 5d2ef5f7-8d4e-426e-bbd1-2364423e5368
* Link: [Gerador UUID](https://geradornv.com.br/gerador-uuid/)

Gerar senha no console do browser:

1. Abra o navegador de sua preferência
2. Com o botão direito clique em "Inspecionar"
3. Clique na aba "Console"
4. Insira `btoa('username:password')`
5. Username e password deve ser substituido por seu usuário e senha que deseja criptografar.

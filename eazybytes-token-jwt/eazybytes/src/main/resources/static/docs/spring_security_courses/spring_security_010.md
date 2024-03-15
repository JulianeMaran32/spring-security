# Method Level Security (Segurança no nível do método)

Até agora, aplicamos regras de autorização nos caminhos/URLs da API usando o Spring Security, mas a segurança no nível
do método permite aplicar essas regras em qualquer camada de um aplicativo, como na camada de serviço, na camada de
repositório etc.

A segurança no nível do método pode ser habilitada usando a anotação `@EnableMethodSecurity` na classe de configuração.

Além disso, a segurança no nível do método também auxilia nas regras de autorização em aplicativos não web, onde não
haverá endpoints.

A segurança no nível do método fornece as seguintes abordagens para aplicar as regras de autorização e executar sua
lógica de negócios:

* **Invocation authorization** (autorização de invocação): valida se um usuário pode invocar um método ou não, com base
  em suas funções/autoridades.
* **Filtering authorization** (autorização de filtragem): valida o que um método pode receber por meio de seus
  parâmetros e o que o invocador pode receber de volta do método após a execução da lógica de negócios.

O Spring Security utilizará os aspectos do módulo AOP e terá os interceptadores inseridos entre a invocação do método
para aplicar as regras de autorização configuradas.

A segurança no nível do método oferece 3 estilos diferentes para configurar as regras de autorização nos métodos:

* A propriedade `prePostEnabled` habilita as anotações `@PreAuthorize` e `@PostAuthorize` do Spring Security.
* A propriedade `securedEnabled` habilita a anotação `@Secured`.
* A propriedade `jsr250Enabled` habilita a anotação `@RoleAllowed`.

```java

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig {

}

```

`@Secured` e `@RoleAllowed` são menos flexíveis em comparação com `@PreAuthorize` e `@PostAuthorize`.

Usando a autorização de invocação, podemos decidir se um usuário está autorizado a invocar um método antes da execução
do método (pré-autorização) ou após a conclusão da execução do método (pós-autorização). Para filtrar os parâmetros
antes de chamar o método, podemos usar `Prefiltering`:

```java

@Service
public class LoansService {

	@PreAuthorize("hasAuthority('VIEWLOANS')")  // O usuário deve ter a autoridade 'VIEWLOANS'
	@PreAuthorize("hasRole('ADMIN')")          // O usuário deve ter a role 'ADMIN'
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // O usuário deve ter alguma das roles 'ADMIN' ou 'USER'
	@PreAuthorize("#username == authentication.principal.username")
	// O username passado deve ser igual ao username do usuário logado
	public Loan getLoanDetails(String username) {
		return loansRepository.loadLoanDetailsByUserName(username);
	}

}
```

Para aplicar regras de pós-autorização, segue um exemplo de configuração:

```java

@Service
public class LoansService {

	@PostAuthorize("returnObject.username == authentication.principal.username")
	// Verifica se o username do objeto retornado é igual ao username do usuário logado
	@PostAuthorize("hasPermission(returnObject, 'ADMIN')")
	// Verifica se o usuário logado possui a permissão 'ADMIN' para o objeto retornado
	public Loan getLoanDetails(String username) {
		return loansRepository.loadLoanDetailsByUserName(username);
	}

}
```

Ao implementar lógica de autorização complexa, podemos separar a lógica usando uma classe separada que
implementa `PermissionEvaluator` e sobrescrever o método `hasPermission()` dentro dela, o qual pode ser aproveitado
dentro das configurações `hasPermission`.

Se temos um cenário onde não queremos controlar a invocação do método, mas queremos garantir que os parâmetros enviados
e recebidos do método sigam regras de autorização ou critérios de filtragem, então podemos considerar a filtragem.

Para filtrar os parâmetros **ANTES**  de chamar o método, podemos usar a anotação `PreFilter`. Porém, é importante notar
que o `filterObject` deve ser do tipo `Collection`.

```java

@RestController
public class ContactController {

	@PreFilter("filterObject.contactName != 'Test'")  // Remove contatos cujo nome seja 'Test'
	public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
		// business logic
		return contacts;
	}

}
```

Para filtrar os parâmetros **DEPOIS** de chamar o método, podemos usar a anotação `PostFilter`. Porém, é importante
notar que o `filterObject` deve ser do tipo `Collection`.

```java

@RestController
public class ContactController {

	@PostFilter("filterObject.contactName != 'Test'")  // Remove contatos cujo nome seja 'Test'
	public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
		// business logic
		return contacts;
	}

}
```

Também podemos usar o `@PostFilter` nos métodos do repositório Spring Data para filtrar quaisquer dados indesejados
provenientes do banco de dados.

---

Conforme explicação acima, foi aplicado na prática o uso de anotação de segurança em nível de método.

Veja o pacote Loans como ficou.

- [Package Loans](./../../../../java/com/springsecurity/eazybytes/loans)
- [Package Contact](./../../../../java/com/springsecurity/eazybytes/contact)
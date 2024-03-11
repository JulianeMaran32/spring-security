# Filtros no Spring Security (Spring Security Filters)

Um filtro é um objeto que realiza tarefas de filtragem na solicitação a um recurso (um servlet ou conteúdo estático) ou
na resposta de um recurso, ou em ambos.

Os filtros realizam a filtragem no método `doFilter`. Cada filtro tem acesso a um objeto `FilterConfig` a partir do qual
ele pode obter seus parâmetros de inicialização, uma referência ao `ServletContext` que pode ser usado, por exemplo,
para carregar recursos necessários para tarefas de filtragem.

Os filtros são configurados no descritor de implementação de um aplicativo web.

Alguns exemplos de filtros incluem:

1. Filtros de Autenticação (Authentication Filter)
2. Filtros de Registro e Auditoria (Logging and Auditing Filters)
3. Filtros de conversão de imagem (Image conversion Filters)
4. Filtros de compactação de dados (Data compression Filters)
5. Filtros de criptografia (Encryption Filters)
6. Filtros de tokenização (Tokenizing Filters)
7. Filtros que disparam eventos de acesso a recursos (Filters that trigger resource access events)
8. Filtros XSL/T (XSL/T filters)
9. Filtro de cadeia de mime-type (Mime-type chain Filter)

## Métodos

* `init()`

Chamado pelo contêiner web para indicar a um filtro que ele está sendo colocado em serviço. O contêiner do servlet chama
o método init exatamente uma vez após a instanciação do filtro. O método init deve ser concluído com êxito antes que o
filtro seja solicitado a fazer qualquer trabalho de filtragem.

O contêiner web não pode colocar o filtro em serviço se o método `init`:

* Lança um `ServletException`
* Não retorna dentro de um período de tempo definido pelo contêiner web

```java
public interface Filter {
	default void init(FilterConfig filterConfig) throws ServletException {
	}
}
```

* `doFilter`

O método `doFilter` do filtro é chamado pelo contêiner sempre que um par de solicitação/resposta é passado pela cadeia
devido a uma solicitação do cliente para um recurso no final da cadeia. O `FilterChain` passado para este método permite
que o filtro passe a solicitação e a resposta para a próxima entidade na cadeia.

Uma implementação típica desse método seguiria o seguinte padrão:

1. Examinar a solicitação
2. Opcionalmente, encapsular o objeto de solicitação com uma implementação personalizada para filtrar conteúdo ou
   cabeçalhos para filtragem de entrada
3. Opcionalmente, encapsular o objeto de resposta com uma implementação personalizada para filtrar conteúdo ou
   cabeçalhos para filtragem de saída
4.

a. **Invoque** a próxima entidade na cadeia usando o objeto `FilterChain` (`chain.doFilter()`), ou
b. **Não** passe o par solicitação/resposta para a próxima entidade na cadeia de filtro para bloquear o processamento da
solicitação

5. Definir cabeçalhos diretamente na resposta após a invocação da próxima entidade na cadeia de filtro.

```java
public interface Filter {
	void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException;
}
```

* `destroy()`

Chamado pelo contêiner web para indicar a um filtro que ele está sendo retirado de serviço. Esse método só é chamado uma
vez que todas as threads dentro do método doFilter do filtro tenham saído ou após um período de tempo limite ter
passado. Depois que o contêiner web chama esse método, ele não chamará o método doFilter novamente nesta instância do
filtro.

Esse método dá ao filtro a oportunidade de liberar quaisquer recursos que estejam sendo mantidos (por exemplo, memória,
descritores de arquivos, threads) e garantir que qualquer estado persistente seja sincronizado com o estado atual do
filtro na memória. A implementação padrão é um NO-OP (operação nula).

```java
public interface Filter {
	default void destroy() {
	}
}
```
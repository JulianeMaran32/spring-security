# LDAP (_Lightweight Directory Access Protocol_)

## LDAP Authentication

O LDAP (_Lightweight Directory Access Protocol_) é frequentemente usado por organizações como um repositório central
para informações do usuário e como um serviço de autenticação. Ele também pode ser usado para armazenar informações de
função para usuários do aplicativo.

A autenticação baseada em LDAP do Spring Security é usada pelo Spring Security quando ele é configurado para aceitar um
nome de usuário/senha para autenticação. No entanto, apesar de usar um nome de usuário e senha para autenticação, ele
não usa `UserDetailsService` porque, na autenticação por vinculação, o servidor LDAP não retorna a senha, então o
aplicativo não pode realizar a validação da senha.

Existem muitos cenários diferentes de como um servidor LDAP pode ser configurado, portanto, o provedor LDAP do Spring
Security é totalmente configurável. Ele usa interfaces de estratégia separadas para autenticação e recuperação de função
e fornece implementações padrão, que podem ser configuradas para lidar com uma ampla variedade de situações.

### Pré-requisitos

Você deve estar familiarizado com LDAP antes de tentar usá-lo com o Spring Security. O seguinte link fornece uma boa
introdução aos conceitos envolvidos e um guia para configurar um diretório usando o servidor LDAP gratuito,
OpenLDAP: https://www.zytrax.com/books/ldap/

Um certo conhecimento das APIs JNDI usadas para acessar LDAP do Java também pode ser útil. Não usamos nenhuma biblioteca
LDAP de terceiros (Mozilla, JLDAP ou outras) no provedor LDAP, mas o Spring LDAP é amplamente utilizado, portanto,
alguma familiaridade com esse projeto pode ser útil se você planeja adicionar suas próprias personalizações.

Ao usar a autenticação LDAP, você deve garantir que configurou corretamente o pool de conexões LDAP. Se você não sabe
como fazer isso, consulte a documentação Java LDAP.

## Configurando um Servidor LDAP Embutido

A primeira coisa que você precisa fazer é garantir que possui um servidor LDAP para o qual apontar sua configuração.
Para simplificar, geralmente é melhor começar com um servidor LDAP embutido. O Spring Security suporta o uso de:

* Servidor UnboundID Embutido
* Servidor ApacheDS Embutido

Nos exemplos a seguir, expomos users.ldif como um recurso do classpath para inicializar o servidor LDAP embutido com
dois usuários, user e admin, ambos com a senha `password`:

```users.ldif
dn: ou=groups,dc=springframework,dc=org
objectclass: top
objectclass: organizationalUnit
ou: groups

dn: ou=people,dc=springframework,dc=org
objectclass: top
objectclass: organizationalUnit
ou: people

dn: uid=admin,ou=people,dc=springframework,dc=org
objectclass: top
objectclass: person
objectclass: organizationalPerson
objectclass: inetOrgPerson
cn: Rod Johnson
sn: Johnson
uid: admin
userPassword: password

dn: uid=user,ou=people,dc=springframework,dc=org
objectclass: top
objectclass: person
objectclass: organizationalPerson
objectclass: inetOrgPerson
cn: Dianne Emu
sn: Emu
uid: user
userPassword: password

dn: cn=user,ou=groups,dc=springframework,dc=org
objectclass: top
objectclass: groupOfNames
cn: user
uniqueMember: uid=admin,ou=people,dc=springframework,dc=org
uniqueMember: uid=user,ou=people,dc=springframework,dc=org

dn: cn=admin,ou=groups,dc=springframework,dc=org
objectclass: top
objectclass: groupOfNames
cn: admin
uniqueMember: uid=admin,ou=people,dc=springframework,dc=org
```

### Servidor UnboundID Embutido

Se desejar usar o `UnboundID`, especifique as seguintes dependências:

* Dependências `UnboundID`

```xml

<dependency>
    <groupId>com.unboundid</groupId>
    <artifactId>unboundid-ldapsdk</artifactId>
    <version>6.0.11</version>
    <scope>runtime</scope>
</dependency>
```

Você pode então configurar o Servidor LDAP Embutido usando um `EmbeddedLdapServerContextSourceFactoryBean`. Isso
instruirá o Spring Security a iniciar um servidor LDAP na memória:

* Configuração do Servidor LDAP Embutido

```java
@Bean
public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean(){
		return EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
		}
```

Como alternativa, você pode configurar manualmente o Servidor LDAP Embutido. Se escolher essa abordagem, será
responsável por gerenciar o ciclo de vida do servidor.

* Configuração Explícita do Servidor LDAP Embutido

```java
@Bean
UnboundIdContainer ldapContainer(){
		return new UnboundIdContainer("dc=springframework,dc=org",
		"classpath:users.ldif");
		}
```

### Servidor ApacheDS Embutido

**Observação:** O Spring Security usa o ApacheDS 1.x, que não é mais mantido. Infelizmente, o ApacheDS 2.x só lançou
versões milestone, sem uma versão estável. Quando uma versão estável do ApacheDS 2.x estiver disponível, consideraremos
a atualização.

Se desejar usar o `ApacheDS`, especifique as seguintes dependências:

* Dependências ApacheDS

```xml

<dependencies>
    <dependency>
        <groupId>org.apache.directory.server</groupId>
        <artifactId>apacheds-core</artifactId>
        <version>1.5.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.apache.directory.server</groupId>
        <artifactId>apacheds-server-jndi</artifactId>
        <version>1.5.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

Você pode então configurar o Servidor LDAP Embutido:

* Configuração do Servidor LDAP Embutido

```java
@Bean
ApacheDSContainer ldapContainer(){
		return new ApacheDSContainer("dc=springframework,dc=org",
		"classpath:users.ldif");
		}
```

## LDAP ContextSource

Depois de ter um servidor LDAP para o qual apontar sua configuração, você precisa configurar o Spring Security para
apontar para um servidor LDAP que deve ser usado para autenticar usuários. Para fazer isso, crie uma Fonte de Contexto
LDAP (que é o equivalente a uma fonte de dados JDBC). Se você já configurou
um `EmbeddedLdapServerContextSourceFactoryBean`, o Spring Security criará uma `ContextSource` LDAP que aponta para o
servidor LDAP embutido.

* Fonte de Contexto LDAP com Servidor LDAP Embutido

```java
@Bean
public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean(){
		EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean=
		EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
		contextSourceFactoryBean.setPort(0);
		return contextSourceFactoryBean;
		}
```

Como alternativa, você pode configurar explicitamente a Fonte de Contexto LDAP para se conectar ao servidor LDAP
fornecido:

* Fonte de Contexto LDAP

```java
ContextSource contextSource(UnboundIdContainer container){
		return new DefaultSpringSecurityContextSource("ldap://localhost:53389/dc=springframework,dc=org");
		}
```

## Authentication

O suporte LDAP do Spring Security não usa o `UserDetailsService` porque a autenticação por vinculação LDAP não permite
que os clientes leiam a senha ou mesmo uma versão hash da senha. Isso significa que não há como uma senha ser lida e
autenticada pelo Spring Security.

Por esse motivo, o suporte LDAP é implementado através da interface LdapAuthenticator. A interface LdapAuthenticator
também é responsável por recuperar quaisquer atributos de usuário necessários. Isso porque as permissões nos atributos
podem depender do tipo de autenticação sendo usada. Por exemplo, se a vinculação for como o usuário, pode ser necessário
ler os atributos com as permissões do próprio usuário.

O Spring Security fornece duas implementações de LdapAuthenticator:

* Usando a autenticação por vinculação
* Usando autenticação de senha

## Usando a autenticação por vinculação

A autenticação por vinculação é o mecanismo mais comum para autenticar usuários com LDAP. Na autenticação por
vinculação, as credenciais do usuário (nome de usuário e senha) são enviadas ao servidor LDAP, que as autentica. A
vantagem de usar a autenticação por vinculação é que os segredos do usuário (a senha) não precisam ser expostos aos
clientes, o que ajuda a protegê-los de vazamentos.

O exemplo a seguir mostra a configuração de autenticação por vinculação:

* Autenticação por vinculação

```java
@Bean
AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource){
		LdapBindAuthenticationManagerFactory factory=new LdapBindAuthenticationManagerFactory(contextSource);
		factory.setUserDnPatterns("uid={0},ou=people");
		return factory.createAuthenticationManager();
		}
```

O exemplo simples anterior obteria o DN para o usuário substituindo o nome de login do usuário no padrão fornecido e
tentando se vincular como aquele usuário com a senha de login. Isso funciona bem se todos os seus usuários estiverem
armazenados em um único nó no diretório. Se, em vez disso, você deseja configurar um filtro de pesquisa LDAP para
localizar o usuário, pode usar o seguinte:

* Autenticação por vinculação com filtro de pesquisa

```java
@Bean
AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource){
		LdapBindAuthenticationManagerFactory factory=new LdapBindAuthenticationManagerFactory(contextSource);
		factory.setUserSearchFilter("(uid={0})");
		factory.setUserSearchBase("ou=people");
		return factory.createAuthenticationManager();
		}
```

Se usado com a definição `ContextSource` mostrada anteriormente, isso realizaria uma pesquisa sob o
DN`ou=people`, `dc=springframework`,` dc=org` usando (`uid={0}`) como filtro. Novamente, o nome de login do usuário é
substituído pelo parâmetro no nome do filtro, então ele procura uma entrada com o atributo uid igual ao nome de usuário.
Se uma base de pesquisa de usuário não for fornecida, a pesquisa será realizada a partir da raiz.

## Usando autenticação de senha

A comparação de senha é quando a senha fornecida pelo usuário é comparada com a armazenada no repositório. Isso pode ser
feito recuperando o valor do atributo de senha e verificando-o localmente ou executando uma operação de “comparação”
LDAP, onde a senha fornecida é passada ao servidor para comparação e o valor real da senha nunca é recuperado. Uma
comparação LDAP não pode ser feita quando a senha é corretamente hash com um salt aleatório.

* Configuração mínima de comparação de senha

```java
@Bean
AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource){
		LdapPasswordComparisonAuthenticationManagerFactory factory=new LdapPasswordComparisonAuthenticationManagerFactory(
		contextSource,NoOpPasswordEncoder.getInstance());
		factory.setUserDnPatterns("uid={0},ou=people");
		return factory.createAuthenticationManager();
		}
```

O exemplo a seguir mostra uma configuração mais avançada com algumas personalizações:

* Configuração de comparação de senha

```java
@Bean
AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource){
		LdapPasswordComparisonAuthenticationManagerFactory factory=new LdapPasswordComparisonAuthenticationManagerFactory(
		contextSource,new BCryptPasswordEncoder());
		factory.setUserDnPatterns("uid={0},ou=people");
		factory.setPasswordAttribute("pwd");
		return factory.createAuthenticationManager();
		}
```

* `factory.setPasswordAttribute("pwd");`: Especifica o atributo de senha como `pwd`.

## `LdapAuthoritiesPopulator` ( populador de autoridades LDAP)

O `LdapAuthoritiesPopulator` do Spring Security é usado para determinar quais autoridades são retornadas para o usuário.
O exemplo a seguir mostra como configurar o `LdapAuthoritiesPopulator`:

* Configuração do `LdapAuthoritiesPopulator`

```java
@Bean
LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource){
		String groupSearchBase="";
		DefaultLdapAuthoritiesPopulator authorities=
		new DefaultLdapAuthoritiesPopulator(contextSource,groupSearchBase);
		authorities.setGroupSearchFilter("member={0}");
		return authorities;
		}

@Bean
AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource,LdapAuthoritiesPopulator authorities){
		LdapBindAuthenticationManagerFactory factory=new LdapBindAuthenticationManagerFactory(contextSource);
		factory.setUserDnPatterns("uid={0},ou=people");
		factory.setLdapAuthoritiesPopulator(authorities);
		return factory.createAuthenticationManager();
		}
```

## Active Directory

O Active Directory oferece suporte a opções de autenticação não padronizadas próprias e o padrão normal de uso não se
encaixa perfeitamente com o `LdapAuthenticationProvider` padrão. Normalmente, a autenticação é realizada usando o nome
de usuário do domínio (no formato `user@domain`), em vez de usar um nome distinto LDAP. Para facilitar isso, o Spring
Security possui um provedor de autenticação personalizado para uma configuração típica do Active Directory.

Configurar o `ActiveDirectoryLdapAuthenticationProvider` é bastante simples. Você só precisa fornecer o nome do domínio
e uma URL LDAP que forneça o endereço do servidor.

**Observação**: Também é possível obter o endereço IP do servidor usando uma pesquisa DNS. Isso atualmente não é
suportado, mas esperamos que esteja em uma versão futura.

O seguinte exemplo configura o Active Directory:

* Exemplo de configuração do Active Directory

```java
@Bean
ActiveDirectoryLdapAuthenticationProvider authenticationProvider(){
		return new ActiveDirectoryLdapAuthenticationProvider("example.com","ldap://company.example.com/");
		}
```

---

## Referência

- [Spring Security: LDAP Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/ldap.html)
- [aceitar um nome de usuário/senha](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html#servlet-authentication-unpwd-input)
- [documentação Java LDAP](https://docs.oracle.com/javase/jndi/tutorial/ldap/connect/config.html)
- [UnboundID](https://ldap.com/unboundid-ldap-sdk-for-java/)
- [ApacheDS](https://directory.apache.org/apacheds/)
- [UserDetailsService](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html#servlet-authentication-userdetailsservice)
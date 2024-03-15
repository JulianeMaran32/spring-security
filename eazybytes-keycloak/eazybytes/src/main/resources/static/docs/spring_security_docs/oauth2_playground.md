### Authorization Code

Passo a passo para utilizar a opção Authorization Code do OAuth 2.0 Playground.

**Fluxo de Autorização OAuth2: Registro de Cliente, Conta de Usuário e Troca de Código**

Este texto descreve o fluxo de autorização OAuth2, incluindo o registro do cliente, conta de usuário e troca do código
de autorização.

* **Client Registration (Registro de Cliente)**
    * client_id fZZMhBzXV-KG3Ho1NuytImDl
    * client_secret EMFNH-yVgaaU6jOpGMvO6b6gpIBbYvixiWVLFM2cRqj7UnhA

* **User Account ()**
    * login modern-elk@example.com
    * password Adorable-Gemsbok-33

1. Build the Authorization URL

(Construindo a URL de Autorização)

Antes da autorização começar, é gerada uma string aleatória para o parâmetro state. O cliente precisa armazenar esse
valor para uso na próxima etapa.

```
https://authorization-server.com/authorize?
 response_type=code
 &client_id=fZZMhBzXV-KG3Ho1NuytImDl
 &redirect_uri=https://www.oauth.com/playground/authorization-code.html
 &scope=photo+offline_access
 &state=lYnDjkuPruO0loxk
```

Para esta demonstração, geramos um parâmetro `state` aleatório (mostrado acima) e o salvamos em um cookie.

Clique em "Autorizar" abaixo para ser direcionado ao servidor de autorização. Você precisará inserir o nome de usuário e
a senha gerados para você.

2. Verify the state parameter

(Verificando o parâmetro state)

O usuário foi redirecionado de volta para o cliente, e você verá alguns parâmetros de consulta adicionais na URL:

```
?state=lYnDjkuPruO0loxk&code=E6NlcfZ6-3rJ-y4E4zOO8j-gugIgG42Hr3OkqdOtLsnBz1Hc
```

Primeiro, você precisa verificar se o parâmetro `state` corresponde ao valor armazenado na sessão deste usuário para se
proteger contra ataques CSRF.

Dependendo de como você armazenou o parâmetro `state` (em um cookie, sessão ou de outra forma), verifique se ele
corresponde ao `state` que você incluiu originalmente na etapa 1. Anteriormente, armazenamos o `state` em um cookie para
esta demonstração.

O `state` armazenado pelo cliente (`lYnDjkuPruO0loxk`) corresponde ao `state` no redirecionamento (`lYnDjkuPruO0loxk`)?
Sim.

3. Exchange the Authorization Code

(Troca do Código de Autorização)

Agora você está pronto para trocar o código de autorização por um token de acesso.

O cliente constrói uma solicitação POST para o token endpoint com os seguintes parâmetros:

```
POST https://authorization-server.com/token

grant_type=authorization_code
&client_id=fZZMhBzXV-KG3Ho1NuytImDl
&client_secret=EMFNH-yVgaaU6jOpGMvO6b6gpIBbYvixiWVLFM2cRqj7UnhA
&redirect_uri=https://www.oauth.com/playground/authorization-code.html
&code=E6NlcfZ6-3rJ-y4E4zOO8j-gugIgG42Hr3OkqdOtLsnBz1Hc
```

Observe que as credenciais do cliente estão incluídas no corpo da solicitação POST neste exemplo. Outros servidores de
autorização podem exigir que as credenciais sejam enviadas como um cabeçalho de autenticação básica HTTP.

4. Token Endpoint Response

Esta é a resposta do token endpoint! A resposta inclui o token de acesso e o token de atualização.

```json
{
  "token_type": "Bearer",
  "expires_in": 86400,
  "access_token": "2B0JRxDa2cRlC7Z0ur1b3OvQDdz4KYhki_BfA8b-RRfx_EOSZo2uIg-YTvnOddzf-KpPCUF8",
  "scope": "photo offline_access",
  "refresh_token": "fAEvgYoi0EqX9QI6Khd3TQml"
}
```

Ótimo! Agora seu aplicativo possui um token de acesso e pode usá-lo para fazer solicitações de API em nome do usuário.

### Implicit

**Fluxo de Autorização OAuth2: Fluxo Implícito**

Este texto descreve o fluxo de autorização OAuth2 usando o Fluxo Implícito, que não é recomendado por motivos de
segurança.

1. Build the Authorization URL

(Construindo a URL de Autorização)

Antes da autorização começar, é gerada uma string aleatória para o parâmetro state. O cliente precisa armazenar esse
valor para uso na próxima etapa.

```
https://authorization-server.com/authorize?
 response_type=token
 &client_id=fZZMhBzXV-KG3Ho1NuytImDl
 &redirect_uri=https://www.oauth.com/playground/implicit.html
 &scope=photo
 &state=N-qlCCiaQRMVJCx0
```

Para esta demonstração, geramos um parâmetro state aleatório (mostrado acima) e o salvamos em um cookie.

Clique em "Autorizar" abaixo para ser direcionado ao servidor de autorização. Você precisará inserir o nome de usuário e
a senha gerados para você.

2. Verify the state parameter

(Verificando o parâmetro state)

O usuário foi redirecionado de volta para o cliente, e você notará que agora há um fragmento na URL que contém o token
de acesso, além de algumas outras informações:

```
#access_token=RBFLFN7Xwlvf6yFid8B6DXJDmBd9CT7ngff-HS3RM2S5F7Abm8dPLJ4mC-HYZB9KOzgLFdF_&token_type=Bearer&expires_in=86400&scope=photos&state=N-qlCCiaQRMVJCx0
```

Primeiro, você precisa verificar se o parâmetro state corresponde ao valor armazenado na sessão deste usuário para se
proteger contra ataques CSRF.

Isso não impede que um agente malicioso injete um token de acesso em seu cliente. Não há solução no OAuth para proteger
o Fluxo Implícito, e ele está sendo descontinuado no Security BCP (Security Best Current Practice).

Dependendo de como você armazenou o parâmetro state (em um cookie, sessão ou de outra forma), verifique se ele
corresponde ao state que você originalmente incluiu na etapa 1. Anteriormente, armazenamos o state em um cookie para
esta demonstração.

O state armazenado pelo cliente (lYnDjkuPruO0loxk) corresponde ao state no redirecionamento (lYnDjkuPruO0loxk)?
Corresponde, continue!

3. Exchange the Authorization Code

(Troca do Código de Autorização)

É isso! Agora que você verificou o parâmetro state, pode começar a usar o token de acesso fornecido no fragmento da URL.

Para referência, aqui estão os valores que o cliente está pronto para usar.

* **access_token**: `RBFLFN7Xwlvf6yFid8B6DXJDmBd9CT7ngff-HS3RM2S5F7Abm8dPLJ4mC-HYZB9KOzgLFdF_`
* **token_type**: `Bearer`
* **expires_in**: `86400`
* **scope**: `photos`

Observe que este é um token de portador OAuth 2.0, o que significa que é opaco para o cliente e o cliente não deve
tentar analisar o token. Alguns servidores de autorização podem usar valores JWT, mas outros podem usar strings
aleatórias. Isso difere de um Token de ID do OpenID Connect, que se destina a ser analisado pelo cliente. Consulte o
OpenID Connect para ver um exemplo de análise de um token de ID.

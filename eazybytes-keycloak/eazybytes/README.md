# EazyBytes

## Keycloak

Foi utilizado a configuração do Keycloak OpenJDK.

1. Efetuar o download do Keycloak.
2. Abrir o terminal e executar o comando `bin\kc.bat start-dev --http-port 8180`
3. Acessar no Browser a URL: http://localhost:8180

## POST /openid-connect/token

### Exemplo de Request

```
POST /realms/eazybankdev/protocol/openid-connect/token HTTP/1.1
Host: localhost:8180
Content-Type: application/x-www-form-urlencoded
Content-Length: 139

client_id=eazybankapi&client_secret=ioabAQA6hsouVtxk1HkYiXZHN168TNrv&scope=openid%20email%20profile%20address&grant_type=client_credentials
```

### Exemplo de Response

```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0Um5LUnpTZGlaYVpTeVNQZExNMUhvYlFXUW9UZXBRMGRPX...",
  "expires_in": 300,
  "refresh_expires_in": 0,
  "token_type": "Bearer",
  "id_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0Um5LUnpTZGlaYVpTeVNQZExNMUhvYlFXUW9UZXBRMGRPX0t...",
  "not-before-policy": 0,
  "scope": "openid address profile email"
}    
```

## GET /openid-connect/auth

### Exemplo de Request

```
GET /realms/eazybankdev/protocol/openid-connect/auth?client_id=eazybankclient&response_type=code&scope=openid&redirect_uri=http://localhost:7080/sample&state=qualquercoisaaqui HTTP/1.1
Host: localhost:8180
```

- Incluir a URL acima no browser e acessar o usuario e senha cadastrados.
- Copiar o que está logo após `code=18f3e685-705c-4a46-b29d-b95c0d730cde.88f7174e-fd66-481f-91a5-004e7fe9f47a.75b3dbcd-6dff-4ff4-b12b-ca6c1c46ba7b`

### Exemplo de Response

```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0Um5LUnpTZGlaYVpTeVNQZExNMUhvYlFXUW9UZXBRMGRPX...",
  "expires_in": 300,
  "refresh_expires_in": 0,
  "token_type": "Bearer",
  "id_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0Um5LUnpTZGlaYVpTeVNQZExNMUhvYlFXUW9UZXBRMGRPX0t...",
  "not-before-policy": 0,
  "scope": "openid address profile email"
}    
```

Acessando os endpoints:

- Configure > Realm settings > Endpoints:
    - OpenID Endpoint Configuration
    - SAML 2.0 Identtiy Provider Metadata

---

## Referência

- [Keycloak](https://www.keycloak.org/)
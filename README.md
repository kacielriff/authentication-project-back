# Authentication Project - Backend

<div align="center">

<a href="https://project-task-management.onrender.com" target="_blank"><img src="https://img.shields.io/badge/-Deploy-%4DC71F?style=for-the-badge&logo=render&logoColor=white" target="_blank"></a>
<img src="https://img.shields.io/badge/-Java-%23ED8B00?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/-Spring-%236DB33F?style=for-the-badge&logo=spring&logoColor=white">

</div>

Este repositório contém a API backend do Authentication Project, um sistema de autenticação utilizando Spring Boot, JWT e PostgreSQL.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.0**
- **Spring Security**
- **Spring Mail**
- **PostgreSQL 16**
- **Hibernate**
- **Spring Data JPA**
- **Lombok**
- **Docker**

---

## Funcionalidades

- Cadastro de usuário
- Autenticação via JWT
- Recuperação de senha
- Proteção de rotas com permissões
- Confirmação de conta via e-mail

## Requisitos

- Java 17+
- PostgreSQL 16+

## Instalação e Configuração

1. Clone o repositório:
   ```sh
   git clone https://github.com/kacielriff/authentication-project-back.git
   cd authentication-project-back
   ```

2. Configure as variáveis de ambiente no arquivo `.env`:
   ```env
    -Dspring.datasource.url=
    -Dspring.datasource.username=
    -Dspring.datasource.password=
    -Djwt.secret=
    -Djwt.expiration=
    -Dspring.mail.username=
    -Dspring.mail.password=
    -Demail.hostname.url=
    -Djwt.expiration.account-confirmation=
    -Djwt.expiration.password-recovery=
   ```

3. Execute a aplicação

---

## Uso

### Registro de Usuário
```http
POST /api/auth/register
```
```json
{
  "name": "Usuário Teste",
  "email": "teste@email.com",
  "password": "Senha123*"
}
```

### Login
```http
POST /api/auth/login
```
```json
{
  "email": "teste@email.com",
  "password": "Senha123*"
}
```

### Confirmação de Criação de Conta
```http
POST /api/auth/confirm-account
```
```json
{
  "token": "eyJhbGciOc6IkCJ9.eyJzdWIiOM0tZSI6.SflKxwRJSMeKQT4fw"
}
```

### Recuperação de Senha
```http
POST /api/auth/forgot-password
```
```json
{
  "email": "teste@email.com"
}
```

### Recuperação de Senha
```http
POST /api/auth/change-password
```
```json
{
  "password": "teste@email.com",
  "token": "eyJhbGciOc6IkCJ9.eyJzdWIiOM0tZSI6.SflKxwRJSMeKQT4fw"
}
```

### Alteração de Senha
```http
PUT /api/auth/change-password
```
```json
{
  "password": "teste@email.com"
}
```

### Informações do Usuário Autenticado
```http
GET /api/auth/logged
```

---

## Contribuição
Sinta-se à vontade para abrir uma *issue* ou enviar um *pull request*!

## Licença
Este projeto está sob a licença MIT.


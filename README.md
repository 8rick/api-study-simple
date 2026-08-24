# API Study Simple

> REST API de produtos desenvolvida com Java e Spring Boot, com foco em fundamentos de desenvolvimento backend, persistência, validação e testes automatizados.

## Sobre o projeto

Projeto criado para praticar a construção de uma API REST seguindo uma estrutura simples e organizada, separando responsabilidades entre **Controller, Service, Repository, DTOs e tratamento de exceções**.

A API permite cadastrar, consultar, atualizar e excluir produtos, além de trabalhar com paginação e validação dos dados recebidos.

## Stack

- **Java 25**
- **Spring Boot 4.1**
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- H2 Database
- SpringDoc OpenAPI / Swagger
- JUnit 5
- Mockito
- MockMvc
- Maven
- GitHub Actions

## Funcionalidades

- Criar produtos
- Listar produtos com paginação
- Atualizar produtos
- Excluir produtos
- Validação de nome e preço
- Tratamento de erros `400` e `404`
- Documentação da API com OpenAPI/Swagger
- Testes unitários e de integração
- Execução automatizada dos testes via GitHub Actions

## Endpoints

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `GET` | `/produtos` | Lista produtos com paginação |
| `POST` | `/produtos` | Cria um produto |
| `PUT` | `/produtos/{id}` | Atualiza um produto |
| `DELETE` | `/produtos/{id}` | Remove um produto |

### Exemplo de criação

```json
{
  "nome": "Notebook",
  "preco": 3500.00
}
```

## Testes

A suíte de testes cobre diferentes camadas da aplicação:

- **Controller:** testes de endpoints, validação e respostas HTTP com MockMvc.
- **Service:** testes das regras de negócio utilizando Mockito.
- **Repository:** testes de persistência utilizando H2 e `@DataJpaTest`.

Para executar todos os testes:

```bash
./mvnw test
```

No Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Documentação da API

Com a aplicação em execução, a documentação interativa pode ser acessada pelo Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Como executar

### Pré-requisitos

- Java 25+
- Git

### Executar o projeto

Clone o repositório e execute:

```bash
git clone https://github.com/8rick/api-study-simple.git
cd api-study-simple
./mvnw spring-boot:run
```

No Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

## Estrutura

```text
src/
├── main/
│   ├── java/estudo/spring_java/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── infra/exception/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   └── resources/
└── test/
    └── java/estudo/spring_java/
        ├── controller/
        ├── repository/
        └── service/
```

## Objetivo

Este projeto faz parte da minha evolução prática em **Java e desenvolvimento backend**, aplicando conceitos de APIs REST, arquitetura em camadas, persistência com JPA, validação, tratamento de exceções, testes automatizados e integração contínua.

---

**Desenvolvido por Rickelmy Barbosa** · [GitHub](https://github.com/8rick)

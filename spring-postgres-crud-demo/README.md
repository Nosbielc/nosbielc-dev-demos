# Spring Boot CRUD com PostgreSQL e Docker

Este projeto demonstra como implementar uma aplicação CRUD (Create, Read, Update, Delete) completa usando Spring Boot, PostgreSQL e Docker, baseada no artigo publicado em [nosbielc.com](https://nosbielc.com/posts/cod-14072025).

## Resumo

A aplicação apresenta um sistema básico de gerenciamento de usuários e produtos, implementando todas as operações CRUD através de uma API REST. Este projeto serve como base fundamental para arquiteturas mais complexas, como replicação master-slave.

O projeto utiliza:
- **Spring Boot 3.5.3** para a aplicação principal
- **Spring Data JPA** para persistência de dados
- **PostgreSQL** como banco de dados
- **Docker Compose** para orquestração de containers
- **Maven** para gerenciamento de dependências

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/nosbielc/dev/
│   │   ├── entity/          # Entidades JPA (User, Product)
│   │   ├── repository/      # Repositórios Spring Data
│   │   ├── service/         # Camada de serviços
│   │   ├── controller/      # Controllers REST
│   │   ├── dto/             # Data Transfer Objects
│   │   └── SpringPostgresCrudDemoApplication.java
│   └── resources/
│       ├── application.yml           # Configuração principal
│       ├── application-local.yml     # Configuração local
│       └── application-docker.yml    # Configuração Docker
└── docker-infra/                     # Infraestrutura Docker
    ├── docker-compose.yml
    ├── Dockerfile
    └── init.sql
```

## Funcionalidades

### Gerenciamento de Usuários
- ✅ Criar usuário
- ✅ Buscar usuário por ID ou email
- ✅ Listar todos os usuários (com paginação)
- ✅ Buscar usuários ativos
- ✅ Pesquisar usuários por nome
- ✅ Atualizar dados do usuário
- ✅ Deletar usuário
- ✅ Desativar usuário

### Gerenciamento de Produtos
- ✅ Criar produto
- ✅ Buscar produto por ID
- ✅ Listar todos os produtos (com paginação)
- ✅ Buscar produtos ativos
- ✅ Pesquisar produtos por nome
- ✅ Filtrar produtos por faixa de preço
- ✅ Buscar produtos com estoque baixo
- ✅ Atualizar dados do produto
- ✅ Atualizar estoque
- ✅ Deletar produto
- ✅ Desativar produto

## Como executar

### Opção 1: PostgreSQL local + Aplicação Spring Boot

1. **Inicie o PostgreSQL com Docker:**
   ```bash
   ./start-postgres.sh
   ```

2. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

### Opção 2: Tudo com Docker

1. **Execute o ambiente completo:**
   ```bash
   ./run-with-docker.sh
   ```

## Endpoints da API

### Usuários
- `POST /api/users` - Criar usuário
- `GET /api/users/{id}` - Buscar por ID
- `GET /api/users/email/{email}` - Buscar por email
- `GET /api/users` - Listar todos
- `GET /api/users/page` - Listar com paginação
- `GET /api/users/active` - Listar ativos
- `GET /api/users/search?name={nome}` - Pesquisar por nome
- `PUT /api/users/{id}` - Atualizar usuário
- `DELETE /api/users/{id}` - Deletar usuário
- `PATCH /api/users/{id}/deactivate` - Desativar usuário

### Produtos
- `POST /api/products` - Criar produto
- `GET /api/products/{id}` - Buscar por ID
- `GET /api/products` - Listar todos
- `GET /api/products/page` - Listar com paginação
- `GET /api/products/active` - Listar ativos
- `GET /api/products/search?name={nome}` - Pesquisar por nome
- `GET /api/products/price-range?minPrice={min}&maxPrice={max}` - Filtrar por preço
- `GET /api/products/low-stock?threshold={limite}` - Produtos com estoque baixo
- `GET /api/products/count` - Contar produtos ativos
- `PUT /api/products/{id}` - Atualizar produto
- `PATCH /api/products/{id}/stock?quantity={qtd}` - Atualizar estoque
- `DELETE /api/products/{id}` - Deletar produto
- `PATCH /api/products/{id}/deactivate` - Desativar produto

## Exemplos de Uso

### Criar um usuário
```bash
curl -X POST http://localhost:8080/api/users \\
  -H "Content-Type: application/json" \\
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "phone": "11999999999"
  }'
```

### Criar um produto
```bash
curl -X POST http://localhost:8080/api/products \\
  -H "Content-Type: application/json" \\
  -d '{
    "name": "Notebook Dell",
    "description": "Notebook Dell Inspiron 15",
    "price": 2500.00,
    "stockQuantity": 10
  }'
```

### Buscar produtos por faixa de preço
```bash
curl "http://localhost:8080/api/products/price-range?minPrice=1000&maxPrice=3000"
```

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Web**
- **Spring Boot Actuator**
- **PostgreSQL 15**
- **HikariCP** (Connection Pool)
- **Lombok**
- **Docker & Docker Compose**
- **Maven**

## Monitoramento

A aplicação expõe endpoints de monitoramento via Spring Actuator:
- `GET /actuator/health` - Status da aplicação
- `GET /actuator/info` - Informações da aplicação
- `GET /actuator/metrics` - Métricas da aplicação

## Configurações do Banco

### Desenvolvimento Local
- **Host:** localhost:5432
- **Database:** crud_demo
- **Usuário:** postgres
- **Senha:** postgres

### Docker
- **Host:** postgres:5432 (interno ao container)
- **Database:** crud_demo
- **Usuário:** postgres
- **Senha:** postgres

## Próximos Passos

Este projeto serve como base para implementações mais avançadas, como:
- Configuração Master-Slave do PostgreSQL
- Cache com Redis
- Autenticação e autorização
- Testes unitários e de integração
- Observabilidade com métricas

## Referência

Baseado no artigo: [Fundamentos Spring Boot com PostgreSQL e Docker](https://nosbielc.com/posts/cod-14072025)

---

**Autor:** Cleibson Gomes | [nosbielc.com](https://nosbielc.com)
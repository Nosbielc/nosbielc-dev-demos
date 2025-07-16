# Spring Boot Master-Slave com PostgreSQL e Docker

Este projeto demonstra como implementar uma arquitetura master-slave com PostgreSQL utilizando Spring Boot e Docker Compose, baseada no artigo publicado em [nosbielc.com](https://nosbielc.com/posts/15072025).

## Resumo

A solução apresentada permite escalar aplicações Java/Spring Boot utilizando replicação de banco de dados PostgreSQL em modo master-slave. O objetivo é separar operações de leitura e escrita, melhorando a performance e a disponibilidade do sistema.

O projeto utiliza:
- **Spring Boot** para a aplicação principal
- **Docker Compose** para orquestração dos containers
- **PostgreSQL** configurado em modo master-slave

## Estrutura do Projeto

- `src/main/java` — Código-fonte da aplicação Spring Boot
- `src/main/resources` — Arquivos de configuração (application.yml, application-docker.yml, etc.)
- `scaling-db-infra/` — Infraestrutura Docker e scripts de configuração do banco
    - `docker-compose.yml` — Orquestração dos containers
    - `master/` — Configurações e scripts do banco master
    - `slave/` — Configurações e scripts do banco slave

## Como funciona

- O container master do PostgreSQL recebe todas as operações de escrita.
- O container slave replica os dados do master e atende operações de leitura.
- A aplicação Spring Boot está configurada para direcionar queries de escrita para o master e de leitura para o slave, utilizando repositórios separados.

## Como executar

1. Clone o repositório
2. Suba a infraestrutura com Docker Compose:
   ```sh
   cd scaling-db-infra
   docker-compose up -d
   ```
3. Execute a aplicação Spring Boot:
   ```sh
   ./mvnw spring-boot:run
   ```

## Testando

- Utilize os endpoints REST para testar operações de leitura e escrita.
- Verifique os logs dos containers para acompanhar a replicação.

## Referência

Baseado no artigo: [Como escalar aplicações Java/Spring Boot com PostgreSQL Master-Slave e Docker](https://nosbielc.com/posts/15072025)

---

Autor: Cleibson Silva (nosbielc.com)


# 💲 Desafio Picpay simplificado

Este projeto é o estudo de uma possível solução para o desafio backend Picpay Simplicado.

## 💻 Tecnologias
- Java 25
- Spring Boot
- Spring Security
- Maven
- MockMvc
- Docker Compose
- Swagger
- Terraform
- Github Actions

## 🖥 Infra

A infra segue práticas de IaC com Terraform para garantir o versionamento e a facilidade de criação/destruição dos recursos. Além disso, a infra é apoiada por uma pipeline CI/CD utilizando o Github Actions para que garantir a praticidade na hora de executar os códigos, fazer correções e garantir a segurança do provisionamento de infraestrutura.

O repositório das soluções de infra está disponível em: [iac-desafio-picpay-simplificado](https://github.com/leonardo-vargas-de-paula/iac-desafio-picpay-simplificado)

## ⏳ Adições futuras

### 🛠️ Desenvolvimento & Arquitetura
- [x] Adicionar o Swagger
- [ ] Testes unitários do Controller _(em progresso...)_
  - [x] UserController
  - [ ] TransactionController
  - [ ] AuthController
- [ ] Testes unitários do Service 
  - [ ]  AuthService
  - [ ]  NotificationService
  - [ ] TransactionService
  - [ ] UserDetailService
  - [ ] UserService
- [x] Criação de uma classe para controle das exceções
- [x] Organizar mapper para conversão de DTOs
- [ ] Ampliar o escopo do projeto

### 🔒 Segurança
- [x] Autenticação com Spring Security

### 🚀 DevOps & Infraestrutura
- [ ] Configuração do CI/CD
- [ ] Deploy na AWS
- [x] Provisionamento de infraestrutura com Terraform





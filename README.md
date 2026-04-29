# 🚀 SkyWallet - Estudo de E-Wallet com Spring Boot

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

Este projeto é um estudo prático focado no desenvolvimento de uma **Carteira Digital (E-Wallet)**. O objetivo principal é aplicar conceitos fundamentais de engenharia de software para sistemas financeiros, garantindo a integridade, consistência e segurança das transações.

---

## 🧠 Conceitos de Engenharia Aplicados

O desenvolvimento desta API foca em resolver desafios reais de movimentação de ativos digitais:

1.  **Atomicidade (ACID):** Uso da anotação `@Transactional` para garantir que transferências entre usuários ocorram como uma operação única. Se o crédito falhar, o débito sofrerá rollback automático.
2.  **Precisão Monetária:** Utilização rigorosa da classe `BigDecimal` para evitar os erros de arredondamento comuns em tipos de ponto flutuante (`Double` ou `Float`).
3.  **Encapsulamento de Regras de Negócio:** Centralização da lógica de transferência na camada de `Service`, mantendo as entidades focadas no domínio.
4.  **Desacoplamento via DTOs:** Uso de *Data Transfer Objects* para proteger as entidades de banco de dados e definir contratos claros com o Front-end.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3.x
* **Banco de Dados:** PostgreSQL (Relacional)
* **Persistência:** Spring Data JPA / Hibernate
* **Gestão de Dependências:** Maven
* **Segurança:** Configuração de CORS para integração com React/Lovable

---

## 🏗️ Estrutura do Projeto

```text
src/main/java/com/e_wallet/demo/
├── controller/    # Endpoints REST da aplicação
├── service/       # Lógica de negócio (Transferências, Cálculos)
├── repository/    # Interfaces de comunicação com PostgreSQL
├── entity/        # Mapeamento das tabelas (User, Wallet)
├── dto/           # Objetos de transferência de dados (JSON)
└── config/        # Configurações globais (CORS, Beans)
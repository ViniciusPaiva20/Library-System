# 📚 Library System

Este projeto tem como objetivo colocar em prática meus conhecimentos da linguagem Java. Ele será atualizado regularmente, com correções de erros e bugs notados por mim ao longo do desenvolvimento.

A ideia é registrar aqui as dificuldades enfrentadas e as soluções encontradas a cada etapa, como uma espécie de diário de desenvolvimento.

---

## 🛠️ Tecnologias

- Java
- Maven
- Jackson

---

## 📌 Status do projeto

🚧 Em desenvolvimento

---

## 📝 Histórico de desenvolvimento

### Commit 1 — Molde base do projeto

Início da base que viria a se tornar este projeto.

**Desafios encontrados:**
- Falta de conhecimento em algumas tecnologias, como Maven e Jackson.
- Dificuldade para compreender tópicos como manipulação, uso e leitura de arquivos JSON — principalmente ao lidar com listas contendo atributos do tipo `LocalDate`.

**Abordagens de resolução:**

Por padrão, o Jackson não sabe serializar e desserializar `LocalDate` no formato de data desejado para o projeto (`dd/MM/yyyy`). A solução foi centralizar a configuração do `ObjectMapper` em uma classe própria, aplicando um *config override* específico para o tipo `LocalDate`, garantindo que todas as datas do sistema sejam lidas e gravadas sempre no mesmo padrão.

Essa classe segue o padrão *Singleton*: o `ObjectMapper` é construído uma única vez e reutilizado em todo o projeto, evitando reconfigurar o mapeamento toda vez que uma serialização ou desserialização for necessária.

---

### Commit 2 — Lógica base do sistema e do aluguel de livros

Implementação da lógica principal do programa, cobrindo o fluxo de cadastro de usuários, aluguel de livros e persistência dos dados.

**Adições:**
- Lógica base do programa, incluindo o menu principal e o fluxo de interação com o usuário via terminal (solução temporária até a implementação de uma UI).
- Lógica de aluguel de livros, com validação de datas de retirada e devolução.
- Métodos de CRUD adicionados à classe `LibraryStock`, responsável por gerenciar o estoque de livros e os aluguéis:
    - Gestão de estoque: adição, leitura, atualização, remoção e busca de livros no estoque (`createNewOrderBook`, `readBookStock`, `updateBookStock`, `deleteBookOrder`, `bookSearch`).
    - Gestão de aluguéis: criação, leitura, atualização e remoção de registros de aluguel, além da busca de um locatário (`Renter`) por e-mail.

**Observações:**
- Por enquanto, a persistência dos dados é feita através de arquivos JSON (`book-stock.json` e `rents-save.json`). A ideia é, futuramente, substituir essa abordagem por um banco de dados.

---

## 📄 Licença

Este projeto está sob a licença MIT.
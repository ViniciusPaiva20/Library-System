# 📚 Library System

Este projeto tem como objetivo colocar em prática meus conhecimentos da linguagem Java. Ele será atualizado regularmente, com correções de erros e bugs notados por mim ao longo do desenvolvimento.

A ideia é registrar aqui as dificuldades enfrentadas e as soluções encontradas a cada etapa, como uma espécie de diário de desenvolvimento.

---

## 🛠️ Tecnologias em uso

- Java
- Maven
- Jackson
- BCrypt (`at.favre.lib.crypto.bcrypt`)

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
    - Gestão de aluguéis: criação, leitura, atualização e remoção de registros de aluguel, além da busca de um locatário (`User`) por e-mail.

**Observações:**
- Por enquanto, a persistência dos dados é feita através de arquivos JSON (`book-stock.json` e `rents-save.json`). A ideia é, futuramente, substituir essa abordagem por um banco de dados.

---

### Commit 3 — Separação de responsabilidades, senhas com hash e novos enums

Neste commit o foco foi organizar o que já existia. No Commit 2, a `Main` e a `LibraryStock` concentravam responsabilidades demais; agora o projeto está dividido em camadas (`controller`, `services`, `dao`, `model` e `config`), e a `Main` passa o trabalho para os controllers, que chamam os services, que usam os DAOs para ler e gravar os arquivos JSON.

**Adições:**
- Controllers (`UserController` e `RentController`) e services (`UserService` e `RentService`), com a lógica de negócio de usuários e aluguéis tirada da `Main` e da `LibraryStock`. A `LibraryUILoader` foi criada vazia, só reservando o lugar da futura UI.
- Novos DAOs: `UserRecord` (usuários, em `saved-users.json`) e `RentRegister` (aluguéis, em `rents-save.json`). A `LibraryStock` ficou focada só no estoque.
- Senhas com BCrypt: elas deixaram de ser guardadas em texto puro, e o `User` agora é criado por `User.createUser`, que valida o e-mail e gera o hash.
- Enums novos e melhorados: `UserStatus` e `UserLevel` foram criados, e as antigas (`BookGenre`, `BookStatus`, `IndicativeRating`) agora aceitam a busca por texto ignorando acentos e maiúsculas/minúsculas.
- No `RentService`, o aluguel confere a idade do usuário contra a classificação do livro, e há métodos para verificar se um aluguel está atrasado (`rentExpired` e `daysRentExpired`). A ideia é que o atraso defina o `UserStatus` do usuário, mas essa ligação automática ainda não foi feita.

**Observações:**
- A interação continua via terminal, como solução temporária até a UI, e algumas opções do menu ainda não foram implementadas.
- A persistência segue em arquivos JSON, agora com um terceiro arquivo para os usuários.

---

## 📄 Licença

Este projeto está sob a licença MIT.

# 📚 Library System

Este projeto tem como objetivo colocar em prática meus conhecimentos da linguagem Java. Ele será atualizado regularmente, com correções de erros e bugs notados por mim ao longo do desenvolvimento.

A ideia é registrar aqui as dificuldades enfrentadas e as soluções encontradas a cada etapa, como uma espécie de diário de desenvolvimento.

---

## 🛠️ Tecnologias em uso

- Java
- Maven
- Jackson
- BCrypt (`at.favre.lib.crypto.bcrypt`)
- MySQL (via JDBC)
- JUnit 5 e Mockito

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
    - Gestão de estoque: adição, leitura, atualização, remoção e busca de livros no estoque (`createNewOrderBook`, `readBookStock`, `updateBookStock`, `delete`, `bookSearch`).
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

### Commit 4 — Migração para MySQL e DAOs por interface

A persistência saiu dos arquivos JSON e passou para um banco MySQL, acessado por JDBC. A divisão em camadas continua (`Main` → controllers → services → DAOs), mas a camada de DAO foi refeita: `LibraryStock`, `UserRecord`, `RentRegister` e `LibraryUILoader` saíram, e o código não lê mais os três arquivos JSON. A futura UI agora está reservada pelas pastas `fxml` e `css`, em `resources`.

**Adições:**
- Classe `Database` (`config`): `getConnection` reaproveita uma única conexão, e `loadProperties` lê os dados de acesso de `src/db.properties`. A `JacksonMapper` segue em `config`, sem mudanças.
- DAOs por interface (`BookDao`, `UserDao`, `RentOrderDao`), com implementações em `dao/impl` para as tabelas `Books`, `Users` e `RentOrders`. Datas são lidas com `getObject` e gravadas com `setObject`; enums são gravados pelo nome da constante. O `delete` e o `bookReturn` do `RentOrderDaoImpl` ainda não foram finalizados.
- Entidades `Book`, `User` e `RentOrder` com identificador próprio e dois construtores cada: um para criar e outro para montar a partir do banco. Novo enum `RentalStatus` (`OPEN`, `COMPLETED`, `LATED`).
- Pacote `security`: `EmailTools.validateEmail` e `PasswordTools` (`generateHash`, com BCrypt e mínimo de 8 caracteres, e `validateHash`). O `User.createUser` deixou de existir; a validação agora fica no `UserController.registerUser`.
- Services e controllers completos para livros, usuários e aluguéis. No `RentService`, `rentABook` e `returnBook` ajustam o estoque dentro de uma transação, e `markLated` marca o aluguel como `LATED` quando `rentExpired` confirma o atraso.
- Primeiro teste, `RentServiceTest` (JUnit 5 e Mockito), cobrindo `rentExpired` e `daysRentExpired`.

**Observações:**
- A interação continua via terminal, como solução temporária até a UI. "Atualizar Cadastro" e "Staff Kit" ainda não foram implementados, e o preço unitário do aluguel está fixo em `10.00` na `Main`.
- O `User.userAppropriateAge` existe, mas ainda não é chamado no aluguel, então a conferência de idade do Commit 3 não está ativa no `RentService`.
- O atraso ainda não altera o `UserStatus`; só o bloqueio de quem já está `RED` (`userCanRentBook`) funciona.
- O `RentController.returnBooks` usa `updateRent`; o `RentService.returnBook`, que repõe o estoque, ainda não é chamado por ele.

---

## 📄 Licença

Este projeto está sob a licença MIT.

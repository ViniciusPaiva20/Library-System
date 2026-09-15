# 📚 Library System

Este projeto tem como objetivo colocar em prática meus conhecimentos da linguagem Java. Ele será atualizado regularmente, com correções de erros e bugs notados por mim ao longo do desenvolvimento.

No momento em que escrevo este README, enviei apenas a base do projeto, ainda em estágio inicial, que será melhorada nos próximos commits. A ideia é registrar aqui as dificuldades enfrentadas e as soluções encontradas a cada etapa, como uma espécie de diário de desenvolvimento.

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

Para o problema do `LocalDate`, o Jackson por padrão não sabe serializar/desserializar esse tipo no formato de data que eu queria (`dd/MM/yyyy`). A solução foi criar uma classe de configuração central para o `ObjectMapper`, aplicando um *config override* específico para `LocalDate`:

```java
package br.com.librarysystem.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;

public class JacksonMapper extends ObjectMapper {

    private static final ObjectMapper INSTANCE = build();

    private static ObjectMapper build() {
        return JsonMapper.builder()
                .withConfigOverride(LocalDate.class, cfg ->
                        cfg.setFormat(JsonFormat.Value.forPattern("dd/MM/yyyy"))).build();
    }

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }

    private JacksonMapper() {}
}
```

Essa classe segue o padrão *Singleton*: o `ObjectMapper` é construído uma única vez (`INSTANCE`), com a formatação de `LocalDate` já configurada, e reutilizado em todo o projeto através de `JacksonMapper.getInstance()`. Isso evita reconfigurar o mapeamento toda vez que uma serialização/desserialização for necessária.

---

## 📄 Licença

Este projeto está sob a licença MIT.

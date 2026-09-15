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

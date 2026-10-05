package br.com.librarysystem.model.enums;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Optional;

public enum UserLevel {

    COMMON("Comum"),
    STAFF("Funcionário"),
    ADMINISTRATOR("Administrador");

    private final String userLevel;

    UserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserLevel() {
        return userLevel;
    }
    // Remove acentos e ignora maiúsculas/minúsculas
    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    public static Optional<UserLevel> fromUserLevel(String text) {
        // Condicional if que garante que não receberá uma texto vazio
        if (text == null || text.isBlank()) return Optional.empty();

        String busca = normalizar(text);

        return Arrays.stream(values())
                .filter(u -> normalizar(u.userLevel).equals(busca)
                        || normalizar(u.name()).equals(busca)
                        || normalizar(u.name().replace('_', ' ')).equals(busca))
                .findFirst();
    }

    // metodo de impressão
    public void allUserLevels() {
        for (UserLevel u: UserLevel.values()) {
            System.out.print(u.getUserLevel());
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return "Nível: " + userLevel;
    }
}

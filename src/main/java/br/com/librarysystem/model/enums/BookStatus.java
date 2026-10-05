package br.com.librarysystem.model.enums;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Optional;

public enum BookStatus {

    AVAILABLE("Disponível"),
    RENTED("Alugado"),
    IN_STOCK("Em estoque");

    private final String bookStatus;

    BookStatus(String status) {
        this.bookStatus = status;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    // Remove acentos e ignora maiúsculas/minúsculas
    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    public static Optional<BookStatus> fromBookStatus(String texto) {
        // Condicional if que garante que não receberá uma texto vazio
        if (texto == null || texto.isBlank()) return Optional.empty();

        String busca = normalizar(texto);

        return Arrays.stream(values())
                .filter(s -> normalizar(s.bookStatus).equals(busca)
                        || normalizar(s.name()).equals(busca)
                        || normalizar(s.name().replace('_', ' ')).equals(busca))
                .findFirst();
    }

    public static void allBookStatus() {
        for (BookStatus s: BookStatus.values()) {
            System.out.print(s.getBookStatus() + "| ");
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return bookStatus;
    }
}

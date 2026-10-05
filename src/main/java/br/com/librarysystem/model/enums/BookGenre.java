package br.com.librarysystem.model.enums;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Optional;

public enum BookGenre {

    TERROR("Terror"),
    ACTION("Ação"),
    AVENTURA("Aventura"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    EDUCATIVO("Educativo"),
    COMEDIA("Comédia");

    private final String bookGenre;

    BookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    // Remove acentos e ignora maiúsculas/minúsculas
    private static String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();
    }

    // Aceita "Comédia", "comedia", "COMEDY", "science_fiction", etc.
    public static Optional<BookGenre> fromBookGenre(String text) {
        // Condicional if que garante que não receberá uma texto vazio
        if (text == null || text.isBlank()) return Optional.empty();

        String busca = normalizar(text);

        return Arrays.stream(values())
                .filter(g -> normalizar(g.bookGenre).equals(busca)
                        || normalizar(g.name()).equals(busca)
                        || normalizar(g.name().replace('_', ' ')).equals(busca))
                .findFirst();
    }

    public static void allBookGenre() {
        for (BookGenre i: BookGenre.values()) {
            System.out.print(i.getBookGenre() + "| ");
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return bookGenre;
    }
}

package br.com.librarysystem.model.enums;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

public enum BookGenre {

    TERROR("Terror"),
    ACTION("Ação"),
    ADVENTURE("Aventura"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    EDUCATIONAL("Educativo"),
    COMEDY("Comédia");

    private final String bookGenre;

    BookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public static BookGenre enumCorresponding(String genreString) {
        if (genreString == null) {
            throw new RuntimeException("O valor está nulo!");
        }
        // tag == tag
        // tag == const
        String genreNormalized = normalizeString(genreString);
        for (BookGenre b: BookGenre.values()) {
            if (normalizeString(b.getBookGenre()).equalsIgnoreCase(genreNormalized)) {
                return b;
            }
            if (genreString.toUpperCase().equals(b.toString())) {
                return b;
            }
        }
        throw new RuntimeException("BookGenre não encontrado!");
    }

    // Normaliza string
    // metodo para pegar
    private static BookGenre findGenre(String stringGenre) {
        HashMap<String, BookGenre> genreList = new HashMap<>();
        for (BookGenre genre: BookGenre.values()) {
            genreList.put(genre.getBookGenre().toLowerCase(), genre);

            if (stringGenre.equalsIgnoreCase(genre.toString())) {
                return genre;
            }
        }

        if (genreList.containsKey(stringGenre.toLowerCase())) {
            return genreList.get(stringGenre.toLowerCase());
        }

        throw new RuntimeException("Não encontrado");
    }

    private static String normalizeString(String originalWord) {
        return Normalizer.normalize(originalWord.trim(), Normalizer.Form.NFC);
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

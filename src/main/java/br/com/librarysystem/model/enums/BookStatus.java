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

    public static BookStatus enumCorresponding(String statusString) {
        if (statusString == null) {
            throw new RuntimeException("O valor não pode ser nulo!");
        }
        String normalizedStatus = normalizeString(statusString);
        for (BookStatus b: BookStatus.values()) {
            if (normalizeString(b.getBookStatus()).equalsIgnoreCase(normalizedStatus)) {
                return b;
            }
        }
        return null;
    }

    // Normaliza string
    private static String normalizeString(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFC);
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

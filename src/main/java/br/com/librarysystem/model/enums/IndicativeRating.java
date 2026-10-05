package br.com.librarysystem.model.enums;

import javafx.scene.Parent;

public enum IndicativeRating {

    FREE(0),
    TEN(10),
    TWELVE(12),
    FOURTEEN(14),
    SIXTEEN(16),
    EIGHTEEN(18);

    private final int bookIndicativeRating;

    IndicativeRating(int indicativeAge) {
        this.bookIndicativeRating = indicativeAge;
    }

    public int getIdadeMinima() {
        return bookIndicativeRating;
    }

    public static IndicativeRating fromIndicativeRating(int codigo) {
        for (IndicativeRating i : values()) {
            if (i.bookIndicativeRating == codigo) {
                return i;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }

    public static void allIndicativeRating() {
        for (IndicativeRating i: IndicativeRating.values()) {
            System.out.print(i + "| ");
        }
        System.out.println("\n");
    }

    @Override
    public String toString() {
        return bookIndicativeRating == 0 ? "Livre" : bookIndicativeRating + " anos";
    }
}

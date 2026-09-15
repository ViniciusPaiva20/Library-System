package br.com.librarysystem.model.enums;

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

    @Override
    public String toString() {
        return bookIndicativeRating == 0 ? "Livre" : bookIndicativeRating + " anos";
    }
}

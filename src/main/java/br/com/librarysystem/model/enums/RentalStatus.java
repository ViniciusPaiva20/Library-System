package br.com.librarysystem.model.enums;

import java.text.Normalizer;

public enum RentalStatus {

    OPEN("Aberta"),
    COMPLETED("Concluída"),
    LATED("Atrasada");

    private final String rentalStatus;

    RentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public static RentalStatus enumCorresponding(String rentalString) {
        if (rentalString == null) {
            throw new RuntimeException("Valor nulo!");
        }
        String normalized = normalize(rentalString);
        for (RentalStatus r: RentalStatus.values()) {
            if (normalize(r.getRentalStatus()).equalsIgnoreCase(normalized)) {
                return r;
            }
        }
        throw new RuntimeException("RentalStatus não correspondido!");
    }

    private static String normalize(String word) {
        return Normalizer.normalize(word.trim(), Normalizer.Form.NFC).toUpperCase();
    }
}

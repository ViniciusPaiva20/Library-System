package br.com.librarysystem.security;

public class EmailTools {

    public static String validateEmail(String email) {
        boolean valide = false;
        for (int i = 0; i < email.length(); i++) {
            char charEmail = email.charAt(i);

            if (charEmail == '@') {
                valide = true;
            }
        }
        if (valide != true) {
            throw new RuntimeException("Email Inválido!");
        }
        return email;
    }
}

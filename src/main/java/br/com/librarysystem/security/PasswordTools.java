package br.com.librarysystem.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordTools {

    public static String generateHash(String noHashPassword) {
        if (noHashPassword.length() < 8) {
            throw new RuntimeException("A senha deve conter no mínimo 8 caracteres");
        }
        return BCrypt.withDefaults().hashToString(12, noHashPassword.toCharArray());
    }

    public static boolean validateHash(String hashPassword, String noHashPassword) {
        if (hashPassword == null || noHashPassword == null) {
            return false;
        }
        return BCrypt.verifyer().verify(noHashPassword.toCharArray(), hashPassword).verified;
    }
}

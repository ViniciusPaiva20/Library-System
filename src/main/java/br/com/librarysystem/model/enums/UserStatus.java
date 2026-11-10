package br.com.librarysystem.model.enums;

public enum UserStatus {

    GREEN("Verde"),
    YELLOW("Amarelo"),
    RED("Vermelho");

    private final String userStatus;

    UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }
}

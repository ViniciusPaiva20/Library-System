package br.com.librarysystem.model.entities;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.librarysystem.model.enums.IndicativeRating;
import br.com.librarysystem.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {

    private String name;
    private int age;
    private String email;
    private String password;
    private UserStatus userStatus;

    public User() {
    }

    private User(@JsonProperty("name") String name,
                 @JsonProperty("age") int age,
                 @JsonProperty("email") String email,
                 @JsonProperty("password") String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.userStatus = UserStatus.GREEN;
    }

    public static User createUser(String name, int age, String email, String password) {
        validateEmail(email);
        return new User(name, age, email, generateHash(password));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = generateHash(password);
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    // metodo
    public boolean userAppropriateAge(IndicativeRating rating) {
        if (age >= rating.getIdadeMinima()) {
            return true;
        }
        return false;
    }

    private static void validateEmail(String email) {
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
    }

    private static String generateHash(String password) {
        if (password.length() < 8) {
            throw new RuntimeException("A senha deve conter no mínimo 8 caracteres");
        }
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean validateHash(String noHashPassword) {
        return BCrypt.verifyer().verify(noHashPassword.toCharArray(), password).verified;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "User: " + name + ", age: " + age + ", email: " + email + "Status User: " + userStatus;
    }
}

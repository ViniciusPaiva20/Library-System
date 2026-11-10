package br.com.librarysystem.model.entities;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.librarysystem.model.enums.IndicativeRating;
import br.com.librarysystem.model.enums.UserLevel;
import br.com.librarysystem.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private Long id;
    private String name;
    private LocalDate dateBirth;
    private String email;
    private String password;
    private UserStatus userStatus;
    private UserLevel userLevel;

    public User() {
    }

    public User(String name, LocalDate dateBirth, String email, String password) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.email = email;
        this.password = password;
        this.userStatus = UserStatus.GREEN;
        this.userLevel = UserLevel.COMMON;
    }
    // construtor para pegar usuário do banco de dados
    public User(Long id, String name, LocalDate dateBirth, String email, String password,
                UserStatus userStatus, UserLevel userLevel) {
        this.id = id;
        this.name = name;
        this.dateBirth = dateBirth;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
        this.userLevel = userLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
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
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserLevel getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    // metodos
    // pega idade atual do usuario
    private int getUserAge() {
        LocalDate today = LocalDate.now();
        return dateBirth.getYear() - today.getYear();
    }

    public boolean userAppropriateAge(IndicativeRating rating) {
        if (getUserAge() >= rating.getIdadeMinima()) {
            return true;
        }
        return false;
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
        return "User: " + name + ", dateBirth: " + dateBirth + ", email: " + email + "Status User: " + userStatus;
    }
}

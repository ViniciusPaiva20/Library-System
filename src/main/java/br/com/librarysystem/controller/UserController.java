package br.com.librarysystem.controller;

import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.security.EmailTools;
import br.com.librarysystem.security.PasswordTools;
import br.com.librarysystem.services.UserService;

import java.time.LocalDate;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User registerUser(String name, LocalDate birthDate, String email, String password) {
        User user = new User(name, birthDate, EmailTools.validateEmail(email), PasswordTools.generateHash(password));
        userService.registerNewUser(user);

        return userService.searchUser(email, password);
    }

    public User userLogin(String userEmail, String userPassword) {
        return userService.userLogin(userEmail, userPassword);
    }

    public void deleteUser(String userEmail, String userPassword) {
        User user = userService.searchUser(userEmail, userPassword);
        userService.deleteUser(user, userPassword);
    }

    public boolean userCanRentBook(User user) {
        if (userService.checkUserStatus(user)) {
            return false;
        }

        return true;
    }

}

package br.com.librarysystem.controller;

import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.services.UserService;

public class UserController {

    private UserService userService = new UserService();

    public User registerUser(String name, int age, String email, String password) {
        User user = User.createUser(name, age, email, password);
        userService.registerNewUser(user);

        return user;
    }

    public User userLogin(String userEmail, String userPassword) {
        return userService.userLogin(userEmail, userPassword);
    }

    public void deleteUser(String userEmail, String userPassword) {
        User user = userService.searchUser(userEmail, userPassword);
        userService.deleteUser(user, userPassword);
    }

    public boolean userCanRentBook(User user) {
        return userService.checkUserStatus(user);
    }

}

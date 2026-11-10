package br.com.librarysystem.services;

import br.com.librarysystem.dao.UserDao;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.enums.UserStatus;
import br.com.librarysystem.security.PasswordTools;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // métodos

    public void registerNewUser(User user) {
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("O Usuario já existe");
        }
        userDao.insert(user);
    }

    public User userLogin(String email, String password) {
        User user = userDao.findByEmail(email);
        if (PasswordTools.validateHash(user.getPassword(), password)) {
            return user;
        }
        throw new RuntimeException("Senha incorreta!");
    }

    public User searchUser(String email, String password) {
        return userDao.findByEmail(email);
    }

    public void deleteUser(User user, String password) {
        userDao.delete(user);
    }

    public boolean checkUserStatus(User user) {
        if (user.getUserStatus().equals(UserStatus.RED)) {
            return false;
        }
        return true;
    }

}

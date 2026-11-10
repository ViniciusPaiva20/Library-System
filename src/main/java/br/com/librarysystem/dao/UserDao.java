package br.com.librarysystem.dao;

import br.com.librarysystem.model.entities.User;

import java.util.List;

public interface UserDao {

    User findById(Long id);
    User findByEmail(String email);
    void insert(User user);
    List<User> listAllUsers();
    void update(User user, String email);
    void delete(User user);
}

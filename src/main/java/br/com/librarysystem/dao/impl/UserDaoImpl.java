package br.com.librarysystem.dao.impl;

import br.com.librarysystem.config.Database;
import br.com.librarysystem.dao.UserDao;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.enums.UserLevel;
import br.com.librarysystem.model.enums.UserStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public User findById(Long id) {
        User user = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE Id=?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("Id"),
                        resultSet.getString("Name"),
                        resultSet.getObject("Birthday", LocalDate.class),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        UserStatus.valueOf(resultSet.getString("UserStatus")),
                        UserLevel.valueOf(resultSet.getString("UserLevel"))
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE Email=?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("Id"),
                        resultSet.getString("Name"),
                        resultSet.getObject("Birthday", LocalDate.class),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        UserStatus.valueOf(resultSet.getString("UserStatus")),
                        UserLevel.valueOf(resultSet.getString("UserLevel"))
                );
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Usuário não encontrado!");
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }
        return user;
    }

    @Override
    public void insert(User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Users (" +
                    "Name, Birthday, Email, Password, UserStatus, UserLevel) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getDateBirth());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getUserStatus().name());
            preparedStatement.setString(6, user.getUserLevel().name());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao registrar o usuário!" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<User> listAllUsers() {
        List<User> userList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users");

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong("Id"),
                        resultSet.getString("Name"),
                        resultSet.getObject("Birthday", LocalDate.class),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        UserStatus.valueOf(resultSet.getString("UserStatus")),
                        UserLevel.valueOf(resultSet.getString("UserLevel"))
                );

                userList.add(user);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro na listagem de usuários! " + ex);
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
        return userList;
    }

    @Override
    public void update(User user, String email) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE Users SET " +
                            "Name=?, Birthday=?, Email=?, Password=?, UserStatus=?, UserLevel=? " +
                        "WHERE Email=?");
            preparedStatement.setString(7, email);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getDateBirth());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getUserStatus().name());
            preparedStatement.setString(6, user.getUserLevel().name());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o usuário! " + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public void delete(User user) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE Email=?");
            preparedStatement.setString(1, user.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Não foi possível deletar o usuário!" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }
}

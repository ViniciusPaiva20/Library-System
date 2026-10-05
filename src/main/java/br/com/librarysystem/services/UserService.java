package br.com.librarysystem.services;

import br.com.librarysystem.dao.RentRegister;
import br.com.librarysystem.dao.UserRecord;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.enums.IndicativeRating;
import br.com.librarysystem.model.enums.UserStatus;

public class UserService {

    private UserRecord userRecord = new UserRecord();

    // métodos

    public void registerNewUser(User user) {
        userRecord.createUser(user);
    }

    public User userLogin(String email, String password) {
        return userRecord.searchUser(email, password);
    }

    public User searchUser(String email, String password) {
        return userRecord.searchUser(email, password);
    }

    public void deleteUser(User user, String password) {
        userRecord.deleteUser(user, password);
    }

    public void checkUserHistory(User user) {
        if (checkUserStatus(user)) {
            System.out.println("O Leitor está com o histórico vermelho!");
        } else {
            System.out.println("O Leitor não está com o histórico vermelho.");
            System.out.print("Seu histórico está: ");
            if (user.getUserStatus().equals(UserStatus.YELLOW)) {
                System.out.println("Amarelo, requer atenção!");
            } else {
                System.out.println("Verde, está tudo certo!");
            }
        }
    }

    public boolean checkUserStatus(User user) {
        if (user.getUserStatus().equals(UserStatus.RED)) {
            System.out.println("O usuário está com status vermelho!");
            return true;
        } else {
            return false;
        }
    }
    // sendo trabalhado
    public void getRentedBooks() {
        RentRegister rentRegister = new RentRegister();

        //rentedBook = rentRegister;
    }

}

package br.com.librarysystem.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.librarysystem.config.JacksonMapper;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.entities.RentalRequest;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRecord {

    public void createUser(User user) {
        List<User> userList = readUsers();
        userList.add(user);

        try (BufferedWriter bw = Files.newBufferedWriter(Path.of("saved-users.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(bw, userList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> readUsers() {
        List<User> userList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of("saved-users.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            userList = mapper.readValue(br, new TypeReference<List<User>>(){});
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return userList;
    }

    public void updateUser(String email, String password) {
        Scanner input = new Scanner(System.in);
        List<User> userList = readUsers();
        User user = searchUser(email, password);

        System.out.println("O quê você quer atualizar? ");
        System.out.println("1 = Nome");
        System.out.println("2 = Email");
        System.out.println("3 = Senha");
        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Digite o novo nome: ");
                String newName = input.nextLine();
                userList.stream().filter(a -> a.getEmail().equals(user.getEmail())).findFirst()
                        .ifPresent(a -> a.setName(newName));
                break;
            case 2:
                System.out.print("Digite seu novo email: ");
                String newEmail = input.nextLine();
                userList.stream().filter(a -> a.getEmail().equals(user.getEmail())).findFirst()
                        .ifPresent(a -> a.setEmail(newEmail));
                break;
            case 3:
                System.out.print("Digite sua nova senha: ");
                String newPassword = input.nextLine();
                userList.stream().filter(a -> a.getEmail().equals(user.getEmail())).findFirst()
                        .ifPresent(a -> a.setPassword(newPassword));
                break;
            default:
                System.out.println("Nenhuma opção válida foi digitada.");
                break;
        }

        try (BufferedWriter bw = Files.newBufferedWriter(Path.of("saved-users.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(bw, userList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        input.close();
    }

    public void deleteUser(User deleteUser, String password) {
        // valida a senha antes de continuar
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), deleteUser.getPassword());
        if (!result.verified) {
            throw new RuntimeException("Senha Incorreta!");
        }

        List<User> userList = readUsers();
        userList.remove(deleteUser);

        try (BufferedWriter bw = Files.newBufferedWriter(Path.of("saved-users.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(bw, userList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public User searchUser(String email, String password) {
        List<User> userList = readUsers();

        User user = userList.stream().filter(u -> u.getEmail()
                .equals(email)).findFirst().orElse(null);

        // checa se o hash da senha digitada bate com o hash armazenado
        if (!user.validateHash(password)) {
            throw new RuntimeException("Senha Incorreta!");
        }

        return user;
    }


}

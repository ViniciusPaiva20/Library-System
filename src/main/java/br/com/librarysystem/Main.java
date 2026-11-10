package br.com.librarysystem;

import br.com.librarysystem.config.Database;
import br.com.librarysystem.controller.BookController;
import br.com.librarysystem.controller.RentController;
import br.com.librarysystem.controller.UserController;
import br.com.librarysystem.dao.BookDao;
import br.com.librarysystem.dao.RentOrderDao;
import br.com.librarysystem.dao.UserDao;
import br.com.librarysystem.dao.impl.BookDaoImpl;
import br.com.librarysystem.dao.impl.RentOrderDaoImpl;
import br.com.librarysystem.dao.impl.UserDaoImpl;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.RentOrder;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.enums.*;
import br.com.librarysystem.services.BookService;
import br.com.librarysystem.services.RentService;
import br.com.librarysystem.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static Scanner input = new Scanner(System.in);

    static void main(String[] args) {
        // aluguel teste
        User loggedUser;

        // Sistema de Login Simples
        int choice;
        System.out.println("Por favor loge ou faça login");
        System.out.println("Login = 1 | Cadastrar-se = 2");
        System.out.print("--");
        choice = input.nextInt();
        input.nextLine();

        if (choice == 1) {
            loggedUser = login();
            System.out.println("Você está logado!");
        } else {
            loggedUser = registerUser();
            System.out.println("Cadastro Concluido!");
        }

        if (loggedUser != null) {
            System.out.println("Bem-vindo ao programa Library_System");
            do {
                System.out.println("1 = Pesquisar Livro");
                System.out.println("2 = Alugar um livro");
                System.out.println("3 = Devolver Livros");
                System.out.println("4 = Atualizar Cadastro");
                if (loggedUser.getUserLevel().equals(UserLevel.STAFF) || loggedUser.getUserLevel().equals(UserLevel.ADMINISTRATOR)) {
                    System.out.println("5 = Staff Kit");
                }
                System.out.println("0 = Sair");
                System.out.println("Oque gostaria de fazer? ");
                System.out.print("--");
                choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        bookSearch();
                        break;
                    case 2:
                        bookRent(loggedUser);
                        break;
                    case 3:
                        returnBooks(loggedUser);
                        break;
                    case 4:
                        // atualizar cadastro em breve
                        break;
                    case 5:
                        if (!loggedUser.getUserLevel().equals(UserLevel.STAFF) || loggedUser.getUserLevel().equals(UserLevel.ADMINISTRATOR)) {
                            throw new RuntimeException();
                        }
                        break;
                    case 0:
                        System.out.println("Encerrando o programa...");
                        break;
                    default:
                        choice = 0;
                        System.out.println("Escolha inválida");
                        break;
                }

            } while (choice != 0);
        }

        input.close();
    }

    static User registerUser() {
        UserDao userDao = new UserDaoImpl(Database.getConnection());
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);

        System.out.print("Informe seu nome: ");
        String userName = input.nextLine();

        System.out.print("Informe a sua data de nascimento: ");
        LocalDate birthDate = LocalDate.parse(input.nextLine(), fmt);

        System.out.print("Informe seu email: ");
        String email = input.nextLine();

        System.out.print("Informe uma senha: ");
        String password = input.nextLine();

        return userController.registerUser(userName, birthDate, email, password);
    }

    static User login() {
        UserDao userDao = new UserDaoImpl(Database.getConnection());
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);

        System.out.println("-Informe os Seguintes Dados-");
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Senha: ");
        String password = input.nextLine();

        return userController.userLogin(email, password);
    }

    static void bookSearch() {

        BookDao bookDao = new BookDaoImpl(Database.getConnection());
        BookService bookService = new BookService(bookDao);
        BookController bookController = new BookController(bookService);
        List<Book> bookList = new ArrayList<>();
        String search;

        System.out.println("Gostaria de Pesquisar Como? ");
        System.out.println("1 - Por Nome");
        System.out.println("2 - Por ISBN");
        System.out.println("3 - Por Autor");
        System.out.print("--");
        int option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                System.out.print("Informe o nome: ");
                search = input.nextLine();
                bookList = bookController.findByTitle(search);
                break;
            case 2:
                System.out.print("Informe o código ISBN: ");
                search = input.nextLine();
                bookList.add(bookController.findByIsbn(search));
                break;
            case 3:
                System.out.print("Informe o nome do autor: ");
                search = input.nextLine();
                bookList = bookController.findByAuthor(search);
                break;
            default:
                System.out.println("Nenhuma opção válida foi digitada");
                break;
        }

        if (bookList.getFirst().getTitle() == null) {
            System.out.println("Livro não encontrado");
        } else {
            for (Book book: bookList) {
                System.out.println(book);
            }
        }
    }

    static void bookRent(User user) {
        try (Connection connection = Database.getConnection()) {
            UserDao userDao = new UserDaoImpl(connection);
            UserService userService = new UserService(userDao);
            UserController userController = new UserController(userService);

            BookDao bookDao = new BookDaoImpl(connection);
            BookService bookService = new BookService(bookDao);
            BookController bookController = new BookController(bookService);

            RentOrderDao requestDao = new RentOrderDaoImpl(connection);
            RentService rentService = new RentService(requestDao, bookDao);
            RentController rentController = new RentController(rentService);

            if (userController.userCanRentBook(user)) {
                throw new RuntimeException("Usuário está marcado em vermelho! Ele não pode alugar um livro");
            }

            System.out.print("Quando pretende devolver o livro? ");
            LocalDate rentRefund = LocalDate.parse(input.nextLine(), fmt);

            System.out.print("Quantos livros você quer alugar? ");
            int quantBook = input.nextInt();
            input.nextLine();

            List<RentOrder> rentList = new ArrayList<>();
            for (int i = 0; i < quantBook; i++) {
                System.out.print("informe o " + (i + 1) + "⁰ livro: ");
                bookSearch();

                System.out.print("Informe o código Isbn do livro: ");
                String bookIsbn = input.nextLine();
                Book book = bookController.findByIsbn(bookIsbn);

                rentList.add(new RentOrder(user.getId(), book.getBookId(), rentRefund, quantBook, "10.00"));
            }
            rentController.rentABook(rentList);

        } catch (SQLException ex) {
            throw new RuntimeException();
        }

    }

    static void returnBooks(User user) {
        try (Connection connection = Database.getConnection()) {
            BookDao bookDao = new BookDaoImpl(connection);

            RentOrderDao requestDao = new RentOrderDaoImpl(connection);
            RentService rentService = new RentService(requestDao, bookDao);
            RentController rentController = new RentController(rentService);


            System.out.println("Quantos livros você vai retornar? ");
            int quant = input.nextInt();
            input.nextLine();

            List<String> isbns = new ArrayList<>();
            for (int i = 0; i < quant; i++) {
                System.out.println("Você precisará informar o código ISBN do livro.");
                System.out.println("Gostaria de pesquisar o código? y = sim, n = não");
                System.out.print("--");
                char searchBook = input.nextLine().charAt(0);
                if (searchBook == 'y') {
                    bookSearch();
                }
                System.out.print("Informe o código Isbn do " + (i +1) + "⁰ livro: ");
                String isbn = input.nextLine();
                isbns.add(isbn);

            }
            rentController.returnBooks(user.getId(), isbns, RentalStatus.COMPLETED);

        } catch (SQLException ex) {
            throw new RuntimeException("Falha no retorno do livro!" + ex);
        }
    }

    static void getAllBook() {
        BookDao bookDao = new BookDaoImpl(Database.getConnection());
        BookService bookService = new BookService(bookDao);
        BookController bookController = new BookController(bookService);

        bookController.listAllBook();
    }
    // ainda será trabalhado
    static void addNewUser() {
        System.out.println();
    }
    // ainda será trabalhado
    static void deleteUser() {

    }

    static void addBookToStock() {
        BookDao bookDao = new BookDaoImpl(Database.getConnection());
        BookService bookService = new BookService(bookDao);
        BookController bookController = new BookController(bookService);

        System.out.print("Quantos livros serão adicionados ao Estoque? ");
        int quantBook = input.nextInt();
        input.nextLine();

        for (int i = 0; i < quantBook; i++) {

            System.out.println("Informe os dados do " + (i + 1) +"⁰ livro");
            System.out.print("Código ISBN: ");
            String isbn = input.nextLine();
            System.out.print("Titulo: ");
            String title = input.nextLine();
            System.out.print("Autor: ");
            String author = input.nextLine();
            System.out.print("Editora: ");
            String publisher = input.nextLine();
            System.out.print("Data de lançamento: ");
            String date = input.nextLine();

            IndicativeRating.allIndicativeRating();
            System.out.print("Idade Indicativa: ");
            String indicativeRating = input.nextLine();

            BookGenre.allBookGenre();
            System.out.print("Genero: ");
            String bookGenre = input.nextLine();

            BookStatus.allBookStatus();
            System.out.print("Estatus do livro: ");
            String bookStatus = input.nextLine();

            System.out.print("Quantidade: ");
            String bookQuant = input.nextLine();

            bookController.registerBook(isbn, author, title, publisher, date, indicativeRating,
                    bookGenre, bookStatus, bookQuant);
        }
    }

    static void removeBookFromStock() {
        BookDao bookDao = new BookDaoImpl(Database.getConnection());
        BookService bookService = new BookService(bookDao);
        BookController bookController = new BookController(bookService);

        System.out.print("Gostaria de pesquisar qual livro deseja remover do estoque(y = sim, n = não)? ");
        System.out.print("--");
        char option = input.nextLine().toLowerCase().charAt(0);
        if (option == 'y') {
            bookSearch();
        }

        System.out.println("Para remover o livro informe o código ISBN do livro correspondente");
        System.out.print("--");
        String isbnCod = input.nextLine();

        bookController.deleteBook(isbnCod);
    }

}

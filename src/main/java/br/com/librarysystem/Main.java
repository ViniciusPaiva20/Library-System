package br.com.librarysystem;

import br.com.librarysystem.controller.RentController;
import br.com.librarysystem.controller.UserController;
import br.com.librarysystem.dao.LibraryStock;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.BookOrder;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.BookStatus;
import br.com.librarysystem.model.enums.IndicativeRating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // aluguel teste
        User loggedUser;

        // Sistema de Login Simples
        int choice;
        System.out.println("Por favor loge ou faça login");
        System.out.println("Login = 1 | Cadastrar-se = 2");
        System.out.print("--");
        choice = in.nextInt();

        if (choice == 1) {
            loggedUser = login();
            System.out.println("Você está logado!");
        } else {
            loggedUser = registerUser();
            System.out.println("Cadastro Concluido!");
        }

        if (loggedUser != null) {
            do {
                System.out.println("Bem-vindo ao programa Library_System");
                System.out.println("1 = Pesquisar Livro");
                System.out.println("2 = Alugar um livro");
                System.out.println("3 = Devolver Livros");
                System.out.println("4 = Atualizar Cadastro");
                System.out.println("5 = Sair");
                System.out.println("8 = Adicionar Livro ao Estoque");
                System.out.println("9 = Remover Livro do Estoque");
                System.out.println("0 = Sair");
                System.out.print("Oque gostaria de fazer? ");
                choice = in.nextInt();
                in.nextLine();

                switch (choice) {
                    case 1:
                        bookSearch();
                        break;
                    case 2:
                        bookRent(loggedUser);
                        break;
                    case 3:
                        // devolver livros em breve
                        break;
                    case 4:
                        // atualizar cadastro em breve
                        break;
                    case 5:
                        choice = 0;
                        break;
                    case 6:
                        // adicionar usuarios
                        break;
                    case 7:
                        // remover usuarios
                        break;
                    case 8:
                        addBookToStock();
                        break;
                    case 9:
                        removeBookFromStock();
                        break;
                    default:
                        choice = 0;
                        System.out.println("Escolha invalida");
                        break;
                }

            } while (choice != 0);
        }

        in.close();
    }

    static User registerUser() {
        Scanner input = new Scanner(System.in);
        UserController userController = new UserController();

        System.out.print("Informe seu nome: ");
        String userName = input.nextLine();

        System.out.print("Informe sua idade: ");
        int age = input.nextInt();
        input.nextLine();

        System.out.print("Informe seu email: ");
        String email = input.nextLine();

        System.out.print("Informe uma senha: ");
        String password = input.nextLine();

        return userController.registerUser(userName, age, email, password);
    }

    static User login() {
        Scanner input = new Scanner(System.in);
        UserController userController = new UserController();

        System.out.println("-Informe os Seguintes Dados-");
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Senha: ");
        String password = input.nextLine();

        return userController.userLogin(email, password);
    }

    static void bookSearch() {
        Scanner input = new Scanner(System.in);
        LibraryStock stock = new LibraryStock();
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
                stock.bookSearch(search, 1);
                break;
            case 2:
                System.out.print("Informe o código ISBN: ");
                search = input.nextLine();
                stock.bookSearch(search, 2);
                break;
            case 3:
                System.out.print("Informe o nome do autor: ");
                search = input.nextLine();
                stock.bookSearch(search, 3);
                break;
            default:
                System.out.println("Nenhuma opção válida foi digitada");
                break;
        }
    }

    static void bookRent(User user) {
        Scanner input = new Scanner(System.in);
        RentController rentController = new RentController();

        System.out.print("Quando pretende devolver o livro? ");
        LocalDate rentRefund = LocalDate.parse(input.nextLine(), fmt);

        rentController.basicSetup(user, rentRefund);

        System.out.print("Quantos livros você quer alugar? ");
        int quantBook = input.nextInt();
        input.nextLine();

        for (int i = 0; i < quantBook; i++) {
            System.out.print("informe o " + (i + 1) + "⁰ livro: ");
            String bookName = input.nextLine();
            rentController.rentABook(bookName);
        }

        rentController.finishRent();
    }
    // ainda será trabalhado
    static void addNewUser() {
        Scanner input = new Scanner(System.in);
        System.out.println();
    }
    // ainda será trabalhado
    static void deleteUser() {

    }

    static void addBookToStock() {
        Scanner input = new Scanner(System.in);
        LibraryStock stock = new LibraryStock();
        List<BookOrder> bookOrder = new ArrayList<>();

        System.out.print("Quantos livros serão adicionados ao Estoque? ");
        int quantBook = input.nextInt();
        input.nextLine();

        for (int i = 0; i < quantBook; i++) {
            System.out.println("Informe os dados do" + (i + 1) +"⁰ livro");
            System.out.print("Titulo: ");
            String title = input.nextLine();
            // Algoritmos e Lógica de Programação
            System.out.print("Autor: ");
            String author = input.nextLine();
            // Gustavo Guanabara
            System.out.print("Código ISBN: ");
            String isbn = input.nextLine();
            // EAGG280926
            System.out.print("Editora: ");
            String publisher = input.nextLine();
            // Curso em Vídeo
            System.out.print("Data de lançamento: ");
            LocalDate date = LocalDate.parse(input.nextLine(), fmt);
            // 01/04/2014
            IndicativeRating.allIndicativeRating();
            System.out.print("Idade Indicativa: ");
            IndicativeRating indicativeRating = IndicativeRating.fromIndicativeRating(input.nextInt());
            input.nextLine();

            BookGenre.allBookGenre();
            System.out.print("Genero: ");
            BookGenre bookGenre = BookGenre.fromBookGenre(input.nextLine()).orElseThrow();

            System.out.print("Quantidade: ");
            int bookQuant = input.nextInt();
            input.nextLine();

            BookStatus.allBookStatus();
            System.out.print("Estatus do livro: ");
            BookStatus bookStatus = BookStatus.fromBookStatus(input.nextLine()).orElseThrow();

            bookOrder.add(new BookOrder(
                    new Book(title, author, isbn, publisher, date, indicativeRating, bookGenre)
                    , bookQuant, bookStatus));
        }

        for (BookOrder o: bookOrder) {
            stock.createNewOrderBook(o.getBook(), o.getQuantityBook(), o.getBookStatus());
        }
    }

    static void removeBookFromStock() {
        Scanner input = new Scanner(System.in);
        LibraryStock stock = new LibraryStock();

        System.out.print("Gostaria de pesquisar qual livro deseja remover do estoque(y = sim, n = não)? ");
        System.out.print("--");
        char option = input.nextLine().toLowerCase().charAt(0);
        if (option == 'y') {
            bookSearch();
        }

        System.out.println("Para remover o livro informe o código ISBN do livro correspondente");
        System.out.print("--");
        String isbnCod = input.nextLine();

        stock.deleteBookOrder(isbnCod);
    }

}

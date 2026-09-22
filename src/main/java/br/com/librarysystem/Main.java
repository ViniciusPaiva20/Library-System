package br.com.librarysystem;

import br.com.librarysystem.dao.LibraryStock;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.Renter;
import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.IndicativeRating;
import br.com.librarysystem.services.BookRentService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        List<Book> list = new ArrayList<>();
        list.add(new Book("Teste 1", "Teste", "BC1020", "Pub"
                , LocalDate.parse("21/03/2003", fmt), IndicativeRating.FREE, BookGenre.DRAMA));
        list.add(new Book("Teste 2", "Master", "BC4010", "Pub"
                , LocalDate.parse("04/07/2006", fmt), IndicativeRating.FREE, BookGenre.TERROR));

        // aluguel teste
        Renter user = new Renter();
        BookRentService bookRent = new BookRentService();
        int choice;
        do {
            System.out.println("Bem-vindo ao programa Library_System");
            System.out.println("1 = Cadastrar um usuario");
            System.out.println("2 = Alugar um livro");
            System.out.println("3 = Salvar");
            System.out.println("4 = Logar");
            System.out.println("5 = Sair");
            System.out.print("Oque gostaria de fazer? ");
            choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    user = cadastroUser();
                    break;
                case 2:
                    bookRent = aluguelLivro(user);
                    break;
                case 3:
                    salvar(bookRent);
                    break;
                case 4:
                    user = logar();
                    break;
                case 5:
                    choice = 0;
                    break;
                default:
                    choice = 0;
                    System.out.println("Escolha invalida");
                    break;
            }

        } while (choice != 0);

        in.close();
    }

    static Renter cadastroUser() {
        Scanner in = new Scanner(System.in);
        System.out.print("Informe seu nome: ");
        String userName = in.nextLine();
        System.out.print("Informe sua idade: ");
        int userAge = in.nextInt();
        in.nextLine();
        System.out.print("Seu email: ");
        String email = in.nextLine();

        return new Renter(userName, userAge, email);
    }

    static BookRentService aluguelLivro(Renter user) {
        Scanner in = new Scanner(System.in);
        System.out.print("Quando pretende devolver o livro? ");
        LocalDate rentRefund = LocalDate.parse(in.nextLine(), fmt);
        BookRentService bookRent = new BookRentService(user, rentRefund);

        System.out.print("Quantos livros você quer alugar? ");
        int quantBook = in.nextInt();

        in.nextLine();
        for (int i = 0; i < quantBook; i++) {
            System.out.println("informe o " + (i + 1) + "⁰ livro");
            String name = in.nextLine();
            bookRent.addBookOrder(bookRent.getBook(name));
        }

        return bookRent;
    }

    static void salvar(BookRentService bookRent) {
        LibraryStock stock = new LibraryStock();
        stock.createNewRent(bookRent);
    }

    static Renter logar() {
        Scanner in = new Scanner(System.in);
        LibraryStock stock = new LibraryStock();
        System.out.print("Informe seu email: ");
        String email = in.nextLine();
        return stock.searchRenter(email);
    }
}

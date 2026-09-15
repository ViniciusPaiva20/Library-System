package br.com.librarysystem;

import br.com.librarysystem.dao.LibraryStock;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.IndicativeRating;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main(String[] args) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Book> list = new ArrayList<>();
        list.add(new Book("Teste 1", "Teste", "BC1020", "Pub"
                , LocalDate.parse("21/03/2003", fmt), IndicativeRating.FREE, BookGenre.DRAMA));
        list.add(new Book("Teste 2", "Master", "BC4010", "Pub"
                , LocalDate.parse("04/07/2006", fmt), IndicativeRating.FREE, BookGenre.TERROR));

        LibraryStock stock = new LibraryStock();

        stock.saveBookListStock(list);
        list = stock.readBookListStock();
        list.forEach(System.out::println);

    }
}

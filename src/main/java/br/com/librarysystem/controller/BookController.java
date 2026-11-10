package br.com.librarysystem.controller;

import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.BookStatus;
import br.com.librarysystem.model.enums.IndicativeRating;
import br.com.librarysystem.services.BookService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookController {

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void registerBook(String isbn, String title, String author, String publisher
            , String releaseDate, String recommendAge, String genre, String status, String quantity) {

        Book book = validateBookData(isbn, title, author, publisher, releaseDate, recommendAge
                ,genre, status, quantity);

        bookService.registerBook(book);
    }

    public Book findByIsbn(String isbn) {
        return bookService.findByIsbn(isbn);
    }

    public List<Book> findByTitle(String title) {
        return bookService.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return bookService.findByAuthor(author);
    }

    public void updateBook(String isbn, String title, String author, String publisher
            , String releaseDate, String recommendAge, String genre, String status, String quantity) {
        bookService.findByIsbn(isbn);

        Book book = validateBookData(isbn, title, author, publisher, releaseDate, recommendAge
                ,genre, status, quantity);

        bookService.updateDataBook(book);
    }

    public void deleteBook(String isbn) {
        Book book = bookService.findByIsbn(isbn);
        bookService.deleteBook(book);
    }

    public void listAllBook() {
        List<Book> bookList = bookService.allBooks();

        for (Book b: bookList) {
            System.out.println(b);
        }
    }
    
    public void returnBook() {

    }

    public Book validateBookData(String bookIsbn, String bookTitle, String bookAuthor, String bookPublisher
            , String bookReleaseDate, String bookRecommendAge, String bookGenre, String bookStatus, String bookQuantity) {
        String isbn = bookIsbn;
        String title = bookTitle;
        String author = bookAuthor;
        String publisher = bookPublisher;
        LocalDate releaseDate;
        IndicativeRating indicativeRating;
        BookGenre genre;
        BookStatus status;
        int quantity;

        try {
            releaseDate = LocalDate.parse(bookReleaseDate, fmt);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Data inválida!");
        }
        try {
            indicativeRating = IndicativeRating.fromIndicativeRating(Integer.parseInt(bookRecommendAge));
        } catch (RuntimeException ex) {
            throw new RuntimeException("Idade indicativa inválida!");
        }
        try {
            genre = BookGenre.enumCorresponding(bookGenre.toUpperCase());
        } catch (RuntimeException ex) {
            throw new RuntimeException("Gênero do livro inválida!");
        }
        try {
            status = BookStatus.enumCorresponding(bookStatus);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Status do livro inválida!");
        }
        try {
            quantity = Integer.parseInt(bookQuantity);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Quantidade inválida!");
        }

        Book book = new Book(isbn, title, author, publisher, releaseDate, indicativeRating
                , genre, status, quantity);

        return book;
    }
}

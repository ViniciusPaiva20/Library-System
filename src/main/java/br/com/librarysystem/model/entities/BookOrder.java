package br.com.librarysystem.model.entities;

import br.com.librarysystem.model.enums.BookStatus;

import java.util.Locale;

public class BookOrder {

    private Book book;
    private Integer quantityBook;
    private BookStatus bookStatus;

    public BookOrder() {
    }

    public BookOrder(Book book, int quantityBook, BookStatus bookStatus) {
        this.book = book;
        this.quantityBook = quantityBook;
        this.bookStatus = bookStatus;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantityBook() {
        return quantityBook;
    }

    public void setQuantityBook(Integer quantityBook) {
        this.quantityBook = quantityBook;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    @Override
    public String toString() {
        return book + ", quantityBook: " + quantityBook + ", status: " + bookStatus.toString().toLowerCase();
    }
}

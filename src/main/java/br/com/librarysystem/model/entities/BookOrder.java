package br.com.librarysystem.model.entities;

import java.util.List;

public class BookOrder {

    private User user;
    private List<Book> bookList;

    public BookOrder() {
    }

    public BookOrder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBookList(Book book) {
        bookList.add(book);
    }

    
}

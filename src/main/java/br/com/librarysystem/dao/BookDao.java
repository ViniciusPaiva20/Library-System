package br.com.librarysystem.dao;

import br.com.librarysystem.model.entities.Book;

import java.util.List;

public interface BookDao {

    Book findById(Long id);
    Book findByIsbn(String isbn);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    void insert(Book book);
    List<Book> getAllBook();
    void update(Book book);
    void delete(Book book);
}

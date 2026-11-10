package br.com.librarysystem.services;

import br.com.librarysystem.dao.BookDao;
import br.com.librarysystem.model.entities.Book;

import java.util.List;

public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book findByIsbn(String isbn) {
        return bookDao.findByIsbn(isbn);
    }

    public List<Book> findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return bookDao.findByAuthor(author);
    }

    public boolean checkAvailability(Book book) {
        if (book.getQuantity() > 0) {
            return true;
        }
        return false;
    }

    public void registerBook(Book book) {
        if (bookDao.findByIsbn(book.getIsbn()).getIsbn() != null) {
            throw new IllegalStateException("Livro já cadastrado!");
        }
        bookDao.insert(book);
    }

    public List<Book> allBooks() {
        return bookDao.getAllBook();
    }

    public void updateDataBook(Book book) {
        if (bookDao.findByIsbn(book.getIsbn()) == null) {
            throw new IllegalStateException("Livro não está cadastrado!");
        }
        bookDao.update(book);
    }

    public void deleteBook(Book book) {
        if (bookDao.findByIsbn(book.getIsbn()) == null) {
            throw new IllegalStateException("Livro não está cadastrado!");
        }

        bookDao.delete(book);
    }
}

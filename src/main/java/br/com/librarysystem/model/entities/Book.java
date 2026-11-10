package br.com.librarysystem.model.entities;

import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.BookStatus;
import br.com.librarysystem.model.enums.IndicativeRating;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Book {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Long bookId;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;
    private IndicativeRating recommendAge;
    private BookGenre bookGenre;
    private BookStatus bookStatus;
    private Integer quantity;

    public Book () {
    }
    // construtor para primeiro uso
    public Book(String isbn, String title, String author, String publisher, LocalDate releaseDate,
                IndicativeRating recommendAge, BookGenre bookGenre, BookStatus bookStatus, Integer quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.recommendAge = recommendAge;
        this.bookGenre = bookGenre;
        this.bookStatus = bookStatus;
        this.quantity = quantity;
    }
    // construtor para quando pegar o livro do banco de dados
    public Book(Long bookId, String isbn, String title, String author, String publisher,
                LocalDate releaseDate, IndicativeRating recommendAge, BookGenre bookGenre,
                BookStatus bookStatus, Integer quantity) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.recommendAge = recommendAge;
        this.bookGenre = bookGenre;
        this.bookStatus = bookStatus;
        this.quantity = quantity;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public IndicativeRating getRecommendAge() {
        return recommendAge;
    }

    public void setRecommendAge(IndicativeRating recommendAge) {
        this.recommendAge = recommendAge;
    }

    public BookGenre getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(BookGenre bookGenre) {
        this.bookGenre = bookGenre;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id: " + bookId +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", releaseDate=" + releaseDate.format(fmt) +
                ", recommendAge=" + recommendAge +
                ", bookGenre=" + bookGenre +
                ", bookStatus=" + bookStatus +
                ", quantity=" + quantity +
                '}';
    }
}

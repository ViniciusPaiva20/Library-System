package br.com.librarysystem.model.entities;

import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.IndicativeRating;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Book {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String title;
    private String author;
    private String isbn;
    private String publisher;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate releaseDate;
    private IndicativeRating recommendAge;
    private BookGenre bookGenre;

    public Book () {
    }

    public Book(String title, String author, String isbn, String publisher,
                LocalDate releaseDate, IndicativeRating recommendAge, BookGenre bookGenre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.recommendAge = recommendAge;
        this.bookGenre = bookGenre;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", releaseDate=" + releaseDate.format(fmt) +
                ", recommendAge=" + recommendAge +
                ", bookGenre=" + bookGenre +
                '}';
    }
}

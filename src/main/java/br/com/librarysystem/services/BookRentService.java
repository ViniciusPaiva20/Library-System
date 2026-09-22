package br.com.librarysystem.services;

import br.com.librarysystem.dao.LibraryStock;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.BookOrder;
import br.com.librarysystem.model.entities.Renter;
import br.com.librarysystem.model.enums.BookStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookRentService {

    private static LibraryStock libraryStock = new LibraryStock();

    private Renter renter;
    private LocalDate rentalDate;
    private LocalDate rentRefund;

    private List<BookOrder> bookOrder = new ArrayList<>();

    public BookRentService() {
    }
    // Construtor
    public BookRentService(Renter renter, LocalDate rentRefund) {
        this.renter = renter;
        this.rentalDate = LocalDate.now();
        this.rentRefund = rentRefund;
        validateDate();
    }
    //Getters e Setters

    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getRentRefund() {
        return rentRefund;
    }

    public void setRentRefund(LocalDate rentRefund) {
        this.rentRefund = rentRefund;
    }

    public List<BookOrder> getBookOrder() {
        return bookOrder;
    }

    public void setBookOrder(List<BookOrder> bookOrder) {
        this.bookOrder = bookOrder;
    }

    // métodos
    public void addBookOrder(Book book) {
        bookOrder.add(new BookOrder(book, 1, BookStatus.RENTED));
    }

    public void rentUpdateService() {
        for (BookOrder o: bookOrder) {
            libraryStock.updateBookStock(o.getBook(), 1);
        }
    }

    public Book getBook(String name) {
        return libraryStock.getBookInStock(name);
    }
    // validar
    public void validateDate() {
        boolean beforeRent = rentalDate.isBefore(LocalDate.now());
        boolean beforeRentRefund = rentRefund.isBefore(LocalDate.now());
        if (beforeRent == true || beforeRentRefund == true) {
            throw new RuntimeException("A data do aluguel não pode ser anterior ao dia atual!");
        }
        if (rentRefund.isEqual(rentalDate)) {
            throw new RuntimeException("A data de devolução não pode ser no mesmo dia da de aluguel!");
        }
    }

    @Override
    public String toString() {
        return "BookRentService{" +
                "renter=" + renter +
                ", rentalDate=" + rentalDate +
                ", rentRefund=" + rentRefund +
                ", bookOrder=" + bookOrder +
                '}';
    }
}

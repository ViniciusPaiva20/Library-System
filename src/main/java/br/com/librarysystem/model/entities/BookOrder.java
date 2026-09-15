package br.com.librarysystem.model.entities;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class BookOrder {

    private Renter renter;
    private List<Book> rentedBooks;
    private LocalDate rentalDate;
    private LocalDate rentRefund;

    public BookOrder() {
    }

    public BookOrder(Renter renter, LocalDate rentalDate, LocalDate rentRefund) {
        this.renter = renter;
        this.rentalDate = rentalDate;
        this.rentRefund = rentRefund;
    }

    public Renter getRenter() {
        return renter;
    }

    public List<Book> getRentedBooks() {
        return Collections.unmodifiableList(rentedBooks);
    }

    public void setRentedBooks(List<Book> rentedBooks) {
        this.rentedBooks = rentedBooks;
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

    public void bookOrder(List<Book> books) {
        this.rentedBooks.addAll(books);
    }

}

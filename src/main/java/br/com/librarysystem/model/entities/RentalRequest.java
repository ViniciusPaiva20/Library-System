package br.com.librarysystem.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalRequest {

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private User user;
    private LocalDate startRent;
    private LocalDate deadline;

    private List<BookOrder> bookOrder = new ArrayList<>();

    public RentalRequest() {
    }

    public RentalRequest(User user, LocalDate rentalDate, LocalDate deadline) {
        this.user = user;
        this.startRent = rentalDate;
        this.deadline = deadline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getRentalDate() {
        return startRent;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.startRent = rentalDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public List<BookOrder> getBookOrder() {
        return bookOrder;
    }

    public void setBookOrder(List<BookOrder> bookOrder) {
        this.bookOrder = bookOrder;
    }
    // adiciona os livros alugados a lista
    public void addBookOrderList(BookOrder order) {
        if (order != null) {
            bookOrder.add(order);
        }
    }

    @Override
    public String toString() {
        return "User: " + user + ", startRentDay: " + fmt.format(startRent) +
                ", deadline: " + fmt.format(deadline) + ", bookOrder: " + bookOrder;
    }
}

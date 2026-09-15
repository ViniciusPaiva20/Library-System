package br.com.librarysystem.services;

import br.com.librarysystem.model.entities.BookOrder;

import java.time.LocalDate;

public class BookRentService {

    private BookOrder bookOrder;

    public BookRentService() {
    }

    public BookRentService(BookOrder bookOrder) {
        this.bookOrder = bookOrder;
    }

    public void checkStock() {

    }

    public void validateDate() {
        boolean beforeRent = bookOrder.getRentalDate().isBefore(LocalDate.now());
        boolean beforeRentRefund = bookOrder.getRentRefund().isBefore(LocalDate.now());
        if (beforeRent == true || beforeRentRefund == true) {
            throw new RuntimeException("A data do aluguel não pode ser anterior ao dia atual!");
        }
        if (bookOrder.getRentRefund().isEqual(bookOrder.getRentalDate())) {
            throw new RuntimeException("A data de devolução não pode ser no mesmo dia da de aluguel!");
        }
    }
}

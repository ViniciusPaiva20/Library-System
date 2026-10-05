package br.com.librarysystem.services;

import br.com.librarysystem.dao.LibraryStock;
import br.com.librarysystem.dao.RentRegister;
import br.com.librarysystem.model.entities.*;
import br.com.librarysystem.model.enums.BookStatus;

import java.time.LocalDate;

public class RentService {

    private RentalRequest rentalRequest;

    private LibraryStock stock = new LibraryStock();

    public RentService() {
    }
    // Construtor
    public RentService(User user, LocalDate deadline) {
        validateDate(LocalDate.now(), deadline);
        rentalRequest = new RentalRequest(user, LocalDate.now(), deadline);
    }

    // serviços
    public void addBookOrder(String nameBook) {
        Book book = stock.getBookFromStock(nameBook);
        User user = rentalRequest.getUser();

        if (user.userAppropriateAge(book.getRecommendAge())) {
            rentalRequest.addBookOrderList(new BookOrder(book, 1, BookStatus.RENTED));
            System.out.println("Livro adicionado");
        }
    }

    public void finishRent() {
        for (BookOrder o: rentalRequest.getBookOrder()) {
            stock.bookRental(o);
        }
        // salvar esse aluguel no arquivo
        RentRegister rentRegister = new RentRegister();
        rentRegister.createNewRent(rentalRequest);
    }
    // validação
    public boolean rentExpired() {
        LocalDate deadline = rentalRequest.getDeadline();
        if (LocalDate.now().isEqual(deadline) || LocalDate.now().isAfter(deadline)) {
            return true;
        } else {
            return false;
        }
    }

    public int daysRentExpired() {
        LocalDate deadline = rentalRequest.getDeadline();
        LocalDate nowDate = LocalDate.now();
        int count = 0;

        if (nowDate.isAfter(deadline)) {
            count = nowDate.minusDays(deadline.getDayOfMonth()).getDayOfMonth();
            return count;
        }
        return count;
    }

    private void validateDate(LocalDate startRent, LocalDate deadline) {
        if (deadline.isBefore(LocalDate.now())) {
            throw new RuntimeException("A data do aluguel não pode ser anterior ao dia atual!");
        }
        if (deadline.isEqual(startRent)) {
            throw new RuntimeException("A data de devolução não pode ser no mesmo dia da de aluguel!");
        }
    }

}

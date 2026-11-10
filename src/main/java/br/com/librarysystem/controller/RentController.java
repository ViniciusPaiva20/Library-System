package br.com.librarysystem.controller;

import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.RentOrder;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.enums.RentalStatus;
import br.com.librarysystem.services.RentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    public List<RentOrder> findRentByUserId(Long userId) {
        return rentService.findRent(userId);
    }

    public void basicSetup(User user, Book book, LocalDate deadline, int quantity, String price) {
        rentService.createNewRent(new RentOrder(user.getId(), book.getBookId(), deadline, quantity, price));
    }

    public void rentABook(List<RentOrder> rentOrder) {
        if (rentService.rentExpired(rentOrder.getFirst(), LocalDate.now())) {
            throw new RuntimeException("Você tem alugueis atrasados ");
        }
        rentService.rentABook(rentOrder);
    }

    public void returnBooks(Long userId, List<String> isbns, RentalStatus rentalStatus) {
        List<RentOrder> rentList = rentService.findRent(userId);
        List<RentOrder> updateRent = new ArrayList<>();

        for (String i: isbns) {
            for (RentOrder o: rentList) {
                long bookId = rentService.bookId(i);
                if (o.getBookID().equals(bookId)) {
                    o.setRentalStatus(rentalStatus);
                    updateRent.add(o);
                }
            }
        }

        rentService.updateRent(updateRent);
    }

    public List<RentOrder> listAllRents() {
        return rentService.listAllRent();
    }

    public void updateRent(Long userId, String isbn, RentalStatus rentalStatus) {
        List<RentOrder> rentList = rentService.findRent(userId);
        List<RentOrder> updateRent = new ArrayList<>();

        for (RentOrder r: rentList) {
            if (r.getRentalStatus().equals(RentalStatus.OPEN)) {
                if (r.getBookID().equals(rentService.bookId(isbn))) {
                    r.setRentalStatus(rentalStatus);
                    updateRent.add(r);
                }
            }
        }
        rentService.updateRent(updateRent);
    }

    public void deleteRent(Long userId, Long bookId) {
        List<RentOrder> rentOrderList = rentService.findRent(userId);
        RentOrder rentOrder = null;
        for (RentOrder o: rentOrderList) {
            if (o.getBookID().equals(bookId)) {
                rentOrder = o;
            }
        }
        if (rentOrder == null) {
            throw new RuntimeException("Aluguel nulo!");
        }
        rentService.deleteRent(rentOrder);
    }

}

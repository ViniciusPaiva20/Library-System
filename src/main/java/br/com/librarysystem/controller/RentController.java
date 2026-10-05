package br.com.librarysystem.controller;

import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.services.RentService;

import java.time.LocalDate;
import java.util.Scanner;


public class RentController {

    private RentService rentService = new RentService();

    public void basicSetup(User user, LocalDate deadline) {
        rentService = new RentService(user, deadline);
    }

    public void rentABook(String bookName) {
        Scanner in = new Scanner(System.in);
        rentService.addBookOrder(bookName);
    }

    public void finishRent() {
        rentService.finishRent();
    }

    public void deleteRent() {

    }

}

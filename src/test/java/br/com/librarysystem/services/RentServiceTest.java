package br.com.librarysystem.services;

import br.com.librarysystem.model.entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class RentServiceTest {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    void mustTalkIfRentIsNotOverdue() {
        // Arrange
        RentService bookRent = new RentService(
                User.createUser("Tester", 20, "tester@gmail.com", "12345678"),
                LocalDate.parse("28/10/2026", fmt));
        // Act
        boolean bookRentValidate = bookRent.rentExpired();
        // Assert
        assertFalse(bookRentValidate);
    }

    @Test
    void youmustInformHowManyDaysItHasExpired() {
        // Arrange
        LocalDate currentDate = LocalDate.now();
        RentService bookRent = new RentService(
                User.createUser("Tester", 20, "tester@gmail.com", "12345678"),
                currentDate.plusDays(1));
        // Act
        int countDays = bookRent.daysRentExpired();
        // Assert
        assertEquals(0, countDays);
    }
}
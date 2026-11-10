package br.com.librarysystem.services;

import br.com.librarysystem.dao.BookDao;
import br.com.librarysystem.dao.RentOrderDao;
import br.com.librarysystem.dao.impl.RentOrderDaoImpl;
import br.com.librarysystem.model.entities.RentOrder;
import br.com.librarysystem.model.enums.RentalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Mock private RentOrderDao rentDao;
    @Mock private BookDao bookDao;
    @InjectMocks private RentService rentService;

    @Test
    void shouldReturnTrueWhenTimeOut() {
        // arrange
        RentOrder rentOrder = new RentOrder(100L, 1L, 23L,
                LocalDate.parse("10/11/2026", fmt),
                LocalDate.parse("10/11/2026", fmt), 1,
                RentalStatus.OPEN, new BigDecimal("7.99"));
        // act
        boolean expired = rentService.rentExpired(rentOrder, LocalDate.parse("11/11/2026", fmt));
        // assert
        assertTrue(expired);
    }

    @Test
    void MustReturnTwentyDays() {
        // arrange
        LocalDate startDate = LocalDate.parse("30/11/2026", fmt);
        LocalDate endDate = LocalDate.parse("10/11/2026", fmt);
        // act
        long intervalOfDays = rentService.daysRentExpired(endDate, startDate);
        // assert
        assertEquals(20, intervalOfDays);
    }
}
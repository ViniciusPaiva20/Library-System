package br.com.librarysystem.services;

import br.com.librarysystem.config.Database;
import br.com.librarysystem.dao.BookDao;
import br.com.librarysystem.dao.RentOrderDao;
import br.com.librarysystem.model.entities.*;
import br.com.librarysystem.model.enums.RentalStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentService {

    private final RentOrderDao rentDao;
    private final BookDao bookDao;

    public RentService(RentOrderDao rentDao, BookDao bookDao) {
        this.rentDao = rentDao;
        this.bookDao = bookDao;
    }

    public Long bookId(String isbn) {
        return bookDao.findByIsbn(isbn).getBookId();
    }

    public List<RentOrder> findRent(Long userId) {
        return rentDao.findRentByUserId(userId);
    }
    // CRUD simples
    public void createNewRent(RentOrder rentOrder) {
        rentDao.insertRentOrder(rentOrder);
    }

    public List<RentOrder> listAllRent() {
        return rentDao.allRents();
    }

    public void updateRent(List<RentOrder> rentOrder) {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);

            for (RentOrder order: rentOrder) {
                rentDao.update(order);
            }
            connection.commit();
            
            connection.setAutoCommit(true);
            
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
    }

    public void deleteRent(RentOrder rentOrder) {
        rentDao.delete(rentOrder);
    }
    // métodos para serviço de alguel
    public void returnBook(List<RentOrder> rentOrder) {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);

            for (RentOrder order: rentOrder) {
                rentDao.update(order);
                Book book = bookDao.findById(order.getBookID());
                int quant = book.getQuantity() + order.getBookQuantity();
                book.setQuantity(quant);
                bookDao.update(book);
            }
            connection.commit();

            connection.setAutoCommit(true);

        } catch (SQLException ex) {
            throw new RuntimeException();
        }
    }

    public void rentABook(List<RentOrder> rentOrder) {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);

            for (RentOrder rents: rentOrder) {
                rentDao.insertRentOrder(rents);
                Book book = bookDao.findById(rents.getBookID());
                book.setQuantity(book.getQuantity() - rents.getBookQuantity());
                bookDao.update(book);
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("Falha ao desfazer a transação", e);
            }
            throw new RuntimeException(ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }

    public boolean rentExpired(RentOrder rentOrder, LocalDate nowDate) {
        LocalDate deadline = rentOrder.getDeadline();

        if (rentOrder.getRentalStatus().equals(RentalStatus.LATED)) {
            return true;
        }
        if (rentOrder.getRentalStatus().equals(RentalStatus.OPEN)) {
            if (daysRentExpired(deadline, nowDate) > 0) {
                return true;
            }
        }

        return false;
    }

    public long daysRentExpired(LocalDate deadline, LocalDate nowDate) {
        if (nowDate.isAfter(deadline)) {
            return ChronoUnit.DAYS.between(deadline, nowDate);
        }
        return 0;
    }

    public void markLated(RentOrder rentOrder) {
        if (rentExpired(rentOrder, LocalDate.now())) {
            rentOrder.setRentalStatus(RentalStatus.LATED);
            rentDao.update(rentOrder);
        }
    }

}

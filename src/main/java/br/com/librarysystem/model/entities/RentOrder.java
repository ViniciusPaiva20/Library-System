package br.com.librarysystem.model.entities;

import br.com.librarysystem.model.enums.RentalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentOrder {

    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Long id;
    private Long userId;
    private Long bookID;
    private LocalDate startRent;
    private LocalDate deadline;
    private Integer bookQuantity;
    private RentalStatus rentalStatus;
    private BigDecimal unitPrice;

    public RentOrder() {
    }

    public RentOrder(Long userId, Long bookID, LocalDate deadline, Integer bookQuantity, String unitPrice) {
        this.userId = userId;
        this.bookID = bookID;
        this.startRent = LocalDate.now();
        this.deadline = deadline;
        this.bookQuantity = bookQuantity;
        this.rentalStatus = RentalStatus.OPEN;
        this.unitPrice = new BigDecimal(unitPrice);
    }
    // construtor para poder pegar o objeto do banco de dados
    public RentOrder(Long id, Long userId, Long bookID, LocalDate startRent, LocalDate deadline, Integer bookQuantity, RentalStatus rentalStatus, BigDecimal unitPrice) {
        this.id = id;
        this.userId = userId;
        this.bookID = bookID;
        this.startRent = startRent;
        this.deadline = deadline;
        this.bookQuantity = bookQuantity;
        this.rentalStatus = rentalStatus;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public LocalDate getStartRent() {
        return startRent;
    }

    public void setStartRent(LocalDate startRent) {
        this.startRent = startRent;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return "RentOrder{" +
                "id=" + id +
                ", user=" + userId +
                ", book=" + bookID +
                ", startRent=" + fmt.format(startRent) +
                ", deadline=" + fmt.format(deadline) +
                ", bookQuantity=" + bookQuantity +
                ", rentalStatus=" + rentalStatus +
                ", unitPrice=" + unitPrice.toPlainString() +
                '}';
    }
}

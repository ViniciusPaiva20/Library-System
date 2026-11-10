package br.com.librarysystem.dao;

import br.com.librarysystem.model.entities.RentOrder;

import java.util.List;

public interface RentOrderDao {

    List<RentOrder> findRentByUserId(Long userId);
    void insertRentOrder(RentOrder rentOrder);
    List<RentOrder> allRents();
    void update(RentOrder rentOrder);
    void delete(RentOrder rentOrder);
    void bookReturn(RentOrder rentOrder);
}

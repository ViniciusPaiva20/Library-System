package br.com.librarysystem.dao.impl;

import br.com.librarysystem.config.Database;
import br.com.librarysystem.dao.RentOrderDao;
import br.com.librarysystem.model.entities.RentOrder;
import br.com.librarysystem.model.enums.RentalStatus;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentOrderDaoImpl implements RentOrderDao {

    private final Connection connection;

    public RentOrderDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<RentOrder> findRentByUserId(Long userId) {
        List<RentOrder> list = new ArrayList<>();
        RentOrder rentOrder;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM RentOrders WHERE UserId=?");
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rentOrder = new RentOrder(
                        resultSet.getLong("Id"),
                        resultSet.getLong("UserId"),
                        resultSet.getLong("BookId"),
                        resultSet.getObject("StartRent", LocalDate.class),
                        resultSet.getObject("Deadline", LocalDate.class),
                        resultSet.getInt("BookQuantity"),
                        RentalStatus.valueOf(resultSet.getString("RentalStatus")),
                        resultSet.getBigDecimal("Price")
                );
                list.add(rentOrder);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro na busca de aluguel pelo Id do usuario" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }

        return list;
    }

    @Override
    public void insertRentOrder(RentOrder rentOrder) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO RentOrders (" +
                    "UserId, BookId, StartRent, Deadline, BookQuantity, RentalStatus, Price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setLong(1, rentOrder.getUserId());
            preparedStatement.setLong(2, rentOrder.getBookID());
            preparedStatement.setObject(3, rentOrder.getStartRent());
            preparedStatement.setObject(4, rentOrder.getDeadline());
            preparedStatement.setInt(5, rentOrder.getBookQuantity());
            preparedStatement.setString(6, rentOrder.getRentalStatus().name());
            preparedStatement.setBigDecimal(7, rentOrder.getUnitPrice());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro na inserção de aluguel" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<RentOrder> allRents() {
        List<RentOrder> rentOrderList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM RentOrders");

            while (resultSet.next()) {
                RentOrder rentOrder = new RentOrder(
                        resultSet.getLong("Id"),
                        resultSet.getLong("UserId"),
                        resultSet.getLong("BookId"),
                        resultSet.getObject("StartRent", LocalDate.class),
                        resultSet.getObject("Deadline", LocalDate.class),
                        resultSet.getInt("BookQuantity"),
                        RentalStatus.enumCorresponding(resultSet.getString("RentalStatus")),
                        resultSet.getBigDecimal("Price")
                );
                rentOrderList.add(rentOrder);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro na leitura de alugueis" + ex);
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }

        return rentOrderList;
    }

    @Override
    public void update(RentOrder rentOrder) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                        "UPDATE RentOrders SET " +
                                "UserId = ?, BookId = ?, StartRent = ?, Deadline = ?, " +
                                "BookQuantity = ?, RentalStatus = ?, Price = ? " +
                            "WHERE Id = ?"
            );
            preparedStatement.setLong(8, rentOrder.getId());

            preparedStatement.setLong(1, rentOrder.getUserId());
            preparedStatement.setLong(2, rentOrder.getBookID());
            preparedStatement.setObject(3, rentOrder.getStartRent());
            preparedStatement.setObject(4, rentOrder.getDeadline());
            preparedStatement.setInt(5, rentOrder.getBookQuantity());
            preparedStatement.setString(6, rentOrder.getRentalStatus().name());
            preparedStatement.setBigDecimal(7, rentOrder.getUnitPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro na atualização do aluguel!" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public void delete(RentOrder rentOrder) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT FROM RentOrders WHERE Id=?");



        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public void bookReturn(RentOrder rentOrder) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO RentOrdes (" +
                    "UserId, BookId, StartRent, Deadline, BookQuantity, RentalStatus, Price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) WHERE Id=?");
            preparedStatement.setLong(8, rentOrder.getId());

            preparedStatement.setLong(1, rentOrder.getUserId());
            preparedStatement.setLong(2, rentOrder.getBookID());
            preparedStatement.setObject(3, rentOrder.getStartRent());
            preparedStatement.setObject(4, rentOrder.getDeadline());
            preparedStatement.setInt(5, rentOrder.getBookQuantity());
            preparedStatement.setString(6, rentOrder.getRentalStatus().name());
            preparedStatement.setBigDecimal(7, rentOrder.getUnitPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }
}

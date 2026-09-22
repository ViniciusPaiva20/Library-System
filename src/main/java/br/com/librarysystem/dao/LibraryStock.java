package br.com.librarysystem.dao;

import br.com.librarysystem.config.JacksonMapper;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.BookOrder;
import br.com.librarysystem.model.entities.Renter;
import br.com.librarysystem.model.enums.BookStatus;
import br.com.librarysystem.services.BookRentService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

public class LibraryStock {

    //Create, Read, Update, Delete dos Livros/BookOrdes
    // Aqui que será salvo novos BookOrders/Adiciona BookOrder ao arquivo existente
    public void createNewOrderBook(Book book, int quantity, BookStatus bookStatus) {
        List<BookOrder> stockList = readBookStock();
        stockList.add(new BookOrder(book, quantity, bookStatus));

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("book-stock.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, stockList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Adicionado ao Estoque com Sucesso!");
    }
    // Lê os BookOrder existente no arquivo
    public List<BookOrder> readBookStock() {
        List<BookOrder> readStock = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of("book-stock.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            readStock = mapper.readValue(br, new TypeReference<List<BookOrder>>(){});
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return readStock;
    }
    // atualiza BookOrders com base nos livros existentes
    public void updateBookStock(Book book, int quantity) {
        List<BookOrder> stockList = readBookStock();
        stockList.stream().filter(o -> o.getBook().equals(book))
                .findFirst().ifPresent(o -> o.setQuantityBook(quantity));

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("book-stock.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, stockList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void  deleteBookOrder(Book book) {
        List<BookOrder> stockList = readBookStock();
        stockList.removeIf(o -> o.getBook().equals(book));
        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("book-stock.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, stockList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Book bookSearch(String nameBook) {
        List<BookOrder> stockList = readBookStock();
        Book book = stockList.stream().filter(o -> o.getBook().getTitle().equals(nameBook))
                .findFirst().get().getBook();
        if (book.getTitle() == null) {
            throw new RuntimeException("Está vazio o cabeçudo");
        }
        return book;
    }

    // Alugueis
    public void createNewRent(BookRentService bookRent) {
        List<BookRentService> rentList = readRents();
        rentList.add(bookRent);
        bookRent.rentUpdateService();
        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("rents-save.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, rentList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Salvo com Sucesso!");
    }

    public List<BookRentService> readRents() {
        List<BookRentService> readRents = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of("rents-save.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            readRents = mapper.readValue(br, new TypeReference<List<BookRentService>>(){});
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return readRents;
    }

    public void updateRents(BookRentService bookRent) {
        List<BookRentService> rentList = readRents();
        rentList.add(bookRent);

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("rents-save.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, rentList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteRent() {
        List<BookRentService> bookRent = readRents();
        bookRent.removeIf(r -> r.getBookOrder().contains(BookStatus.RENTED));

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("rents-save.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, bookRent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Renter searchRenter(String email) {
        List<BookRentService> bookRent = readRents();

        Renter user = bookRent.stream().filter(r -> r.getRenter()
                .getEmail().equals(email)).findFirst().map(BookRentService::getRenter).get();

        return user;
    }

    public void bookReservation(BookRentService bookRent) {
        bookRent.rentUpdateService();
        updateRents(bookRent);
    }

    public Book getBookInStock(String name) {
        List<BookOrder> bookOrders = readBookStock();
        for (BookOrder b: bookOrders) {
            if (b.getBook().getTitle().equals(name)) {
                return b.getBook();
            }
        }
        return null;
    }
}

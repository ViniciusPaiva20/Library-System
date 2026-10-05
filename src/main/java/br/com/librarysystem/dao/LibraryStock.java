package br.com.librarysystem.dao;

import br.com.librarysystem.config.JacksonMapper;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.entities.BookOrder;
import br.com.librarysystem.model.enums.BookStatus;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LibraryStock {

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

    public void  deleteBookOrder(String isbnCod) {
        List<BookOrder> stockList = readBookStock();
        stockList.removeIf(o -> o.getBook().getIsbn().equals(isbnCod));
        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("book-stock.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, stockList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Livro removido com sucesso! ");
    }

    public void bookSearch(String search, int option) {
        List<BookOrder> stockList = readBookStock();
        switch (option) {
            case 1:
                for (BookOrder o: stockList) {
                    if (o.getBook().getTitle().equals(search)) {
                        System.out.println(o.getBook().toString());
                    }
                }
                break;
            case 2:
                for (BookOrder o: stockList) {
                    if (o.getBook().getIsbn().equals(search)) {
                        System.out.println(o.getBook());
                    }
                }
                break;
            case 3:
                for (BookOrder o: stockList) {
                    if (o.getBook().getAuthor().equals(search)) {
                        System.out.println(o.getBook());
                    }
                }
                break;
        }
    }

    public Book getBookFromStock(String bookName) {
        List<BookOrder> stock = readBookStock();

        return stock.stream().filter(o -> o.getBook().getTitle().equals(bookName)).findFirst()
                .map(BookOrder::getBook).get();
    }

    // Alugueis

    public void bookRental(BookOrder bookOrder) {
        List<BookOrder> stock = readBookStock();

        stock.stream().filter(s -> s.getBook().equals(bookOrder.getBook())).findFirst()
                .ifPresent(s -> s.setQuantityBook(s.getQuantityBook() -1));

        try (BufferedWriter bw = Files.newBufferedWriter(Path.of("book-stock.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(bw, stock);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}

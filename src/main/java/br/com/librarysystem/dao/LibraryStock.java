package br.com.librarysystem.dao;

import br.com.librarysystem.config.JacksonMapper;
import br.com.librarysystem.model.entities.Book;
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

    public void saveBookListStock(List<Book> bookList) {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of("book-stock.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(bw, bookList);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Book> readBookListStock() {
        List<Book> stockList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Path.of("book-stock.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            stockList = mapper.readValue(br, new TypeReference<List<Book>>(){});

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return stockList;
    }
}

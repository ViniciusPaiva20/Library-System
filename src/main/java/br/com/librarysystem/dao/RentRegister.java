package br.com.librarysystem.dao;

import br.com.librarysystem.config.JacksonMapper;
import br.com.librarysystem.model.entities.User;
import br.com.librarysystem.model.entities.RentalRequest;
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

public class RentRegister {

    public void createNewRent(RentalRequest requestCreateRent) {
        List<RentalRequest> rentList = readRents();
        rentList.add(requestCreateRent);

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("rents-save.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, rentList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Salvo com Sucesso!");
    }

    public List<RentalRequest> readRents() {
        List<RentalRequest> rents = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of("rents-save.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            rents = mapper.readValue(br, new TypeReference<List<RentalRequest>>(){});
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return rents;
    }

    public void updateRents(RentalRequest requestUpdateRent) {
        List<RentalRequest> rentList = readRents();
        rentList.add(requestUpdateRent);

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("rents-save.json"))) {
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, rentList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteRent() {
        List<RentalRequest> rents = readRents();
        rents.removeIf(r -> r.getBookOrder().contains(BookStatus.RENTED));

        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of("rents-save.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            mapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, rents);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public RentalRequest searchRent(User user) {
        List<RentalRequest> rentsList = readRents();
        RentalRequest rent = new RentalRequest();

        try (BufferedReader br = Files.newBufferedReader(Path.of("rents-save.json"))){
            ObjectMapper mapper = JacksonMapper.getInstance();

            rent = rentsList.stream().filter(r -> r.getUser().equals(user))
                    .findFirst().get();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return rent;
    }

}

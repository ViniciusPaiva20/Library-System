package br.com.librarysystem.dao.impl;

import br.com.librarysystem.config.Database;
import br.com.librarysystem.dao.BookDao;
import br.com.librarysystem.model.entities.Book;
import br.com.librarysystem.model.enums.BookGenre;
import br.com.librarysystem.model.enums.BookStatus;
import br.com.librarysystem.model.enums.IndicativeRating;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
    Essa é classe que Implementa o BookDao, para poder lidar com tipos LocalDate e Enum
    foi necessário e adequado o uso de getObject e setObject da API JDBC.

    E para a busca de livros julguei como correto e prático buscar pelo o código ISBN
    provavelmente não é o ideal, mas por hora será assim.
 */

public class BookDaoImpl implements BookDao {

    private final Connection connection;

    public BookDaoImpl(Connection connection) {
        this.connection = connection;
    }



    @Override
    public Book findById(Long id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Book book = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Books WHERE Id=?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = new Book(resultSet.getLong("Id"),
                        resultSet.getString("Isbn"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("Publisher"),
                        resultSet.getObject("ReleaseDate", LocalDate.class),
                        IndicativeRating.fromIndicativeRating(resultSet.getInt("RecommendAge")),
                        BookGenre.valueOf(resultSet.getString("BookGenre")),
                        BookStatus.valueOf(resultSet.getString("BookStatus")),
                        resultSet.getInt("BookQuantity")
                );
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Busca por Id falhou!" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }

        return book;
    }

    @Override
    public Book findByIsbn(String isbn) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Book book = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Books WHERE Isbn=?");
            preparedStatement.setString(1, isbn);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = new Book(resultSet.getLong("Id"),
                        resultSet.getString("Isbn"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("Publisher"),
                        resultSet.getObject("ReleaseDate", LocalDate.class),
                        IndicativeRating.fromIndicativeRating(resultSet.getInt("RecommendAge")),
                        BookGenre.valueOf(resultSet.getString("BookGenre")),
                        BookStatus.valueOf(resultSet.getString("BookStatus")),
                        resultSet.getInt("BookQuantity")
                );
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Busca por Isbn falhou!" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }

        return book;
    }

    @Override
    public List<Book> findByTitle(String title) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> bookList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Books WHERE Title=?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bookList.add(new Book(resultSet.getLong("Id"),
                        resultSet.getString("Isbn"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("Publisher"),
                        resultSet.getObject("ReleaseDate", LocalDate.class),
                        IndicativeRating.fromIndicativeRating(resultSet.getInt("RecommendAge")),
                        BookGenre.valueOf(resultSet.getString("BookGenre")),
                        BookStatus.valueOf(resultSet.getString("BookStatus")),
                        resultSet.getInt("BookQuantity")
                ));
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Busca por Titulo falhou!" + ex);
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }

        return bookList;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Book> bookList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Books WHERE Author=?");
            preparedStatement.setString(1, author);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bookList.add(new Book(resultSet.getLong("Id"),
                        resultSet.getString("Isbn"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("Publisher"),
                        resultSet.getObject("ReleaseDate", LocalDate.class),
                        IndicativeRating.fromIndicativeRating(resultSet.getInt("RecommendAge")),
                        BookGenre.valueOf(resultSet.getString("BookGenre")),
                        BookStatus.valueOf(resultSet.getString("BookStatus")),
                        resultSet.getInt("BookQuantity")
                ));
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro na busca! " + ex);
        } finally {
            Database.closeStatement(preparedStatement);
            Database.closeResultSet(resultSet);
        }

        return bookList;
    }

    @Override
    public void insert(Book book) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO Books (" +
                    "Isbn, Title, Author, Publisher, ReleaseDate, RecommendAge, BookGenre, BookStatus, BookQuantity) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setObject(5, book.getReleaseDate());
            preparedStatement.setInt(6, book.getRecommendAge().getIdadeMinima());
            preparedStatement.setString(7, book.getBookGenre().name());
            preparedStatement.setObject(8, book.getBookStatus().name());
            preparedStatement.setInt(9, book.getQuantity());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Falha na criação do Livro! " + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public List<Book> getAllBook() {
        List<Book> books = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Books");

            while (resultSet.next()) {
                Book book = new Book(resultSet.getLong("Id"),
                        resultSet.getString("Isbn"),
                        resultSet.getString("Title"),
                        resultSet.getString("Author"),
                        resultSet.getString("Publisher"),
                        resultSet.getObject("ReleaseDate", LocalDate.class),
                        IndicativeRating.fromIndicativeRating(resultSet.getInt("RecommendAge")),
                        BookGenre.valueOf(resultSet.getString("BookGenre")),
                        BookStatus.valueOf(resultSet.getString("BookStatus")),
                        resultSet.getInt("BookQuantity")
                );

                books.add(book);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Não foi encontrado nenhum livro! " + ex);
        } finally {
            Database.closeStatement(statement);
            Database.closeResultSet(resultSet);
        }
        return books;
    }

    @Override
    public void update(Book book) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE Books SET " +
                            "Title=?, Author=?, Publisher=?, " +
                            "ReleaseDate=?, RecommendAge=?, BookGenre=?, BookStatus=?, " +
                            "BookQuantity=? " +
                        "WHERE Isbn=?");
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setObject(4, book.getReleaseDate());
            preparedStatement.setInt(5, book.getRecommendAge().getIdadeMinima());
            preparedStatement.setString(6, book.getBookGenre().name());
            preparedStatement.setString(7, book.getBookStatus().name());
            preparedStatement.setInt(8, book.getQuantity());

            preparedStatement.setString(9, book.getIsbn());

            // faz updates das informações
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Falha na atualização do livro! " + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }

    @Override
    public void delete(Book book) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Books WHERE Isbn=?");
            preparedStatement.setString(1, book.getIsbn());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Falha ao deletar o livro! " + ex);
        } finally {
            Database.closeStatement(preparedStatement);
        }
    }
}

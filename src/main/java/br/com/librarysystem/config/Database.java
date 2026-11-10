package br.com.librarysystem.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class Database {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");

                conn = DriverManager.getConnection(url, props);
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao conectar no banco: " + ex.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar a conexão", ex);
            } finally {
                conn = null;
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Properties loadProperties() {
        try (InputStream fs = new FileInputStream("src/db.properties")) {
            if (fs == null) {
                throw new RuntimeException("Arquivo db.properties não encontrado!");
            }
            Properties props = new Properties();
            props.load(fs);

            return props;
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao ler db.properties", ex);
        }
    }

}

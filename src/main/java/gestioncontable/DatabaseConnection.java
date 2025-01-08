package gestioncontable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "";
    private static final String USER = "";
   private static final String PASSWORD = "";

    //https://jdbc.postgresql.org/download/
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el controlador de PostgreSQL", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

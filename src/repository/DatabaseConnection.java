package repository;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String DATABASE_URL = dotenv.get("DATABASE_URL");
    private static final String DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
    private static final String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package services;

import models.User;
import dao.UserDao;
import dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;
    public Connection connection;

    public UserService() {
        this.userDao = new UserDao();
        this.connection = DatabaseConnection.getConnection();
    }

    public User[] getAllUsers() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return userDao.getAllUsers(conn);
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch users: " + e.getMessage());
            return new User[0];
            
        }
    }
}

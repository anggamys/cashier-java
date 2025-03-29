package services;

import models.User;
import dao.UserDao;
import dao.DatabaseConnection;
import dao.TransactionManager;

import java.sql.SQLException;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User[] getAllUsers() {
        try (TransactionManager tx = new TransactionManager(DatabaseConnection.getConnection())) {
            return userDao.getAllUsers(tx.getConnection());
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch users: " + e.getMessage());
            return new User[0];  
        }
    }
}

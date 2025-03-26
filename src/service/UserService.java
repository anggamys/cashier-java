package service;

import dao.UserDAO;
import model.User;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public void registerUser(String username, String password, String role) {
        User user = new User(0, username, password, role);
        userDAO.registerUser(user);
    }

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean isAdmin(User user) {
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public boolean isUser(User user) {
        return user != null && "USER".equalsIgnoreCase(user.getRole());
    }
}

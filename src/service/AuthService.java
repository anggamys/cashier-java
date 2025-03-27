package service;

import dao.UserDAO;
import model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public void registerUser(String username, String password, String role, String name, String email, String phone, String address) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address);

        userDAO.registerUser(user);
        
    }

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean isCashier(User user) {
        return user != null && "CASHIER".equalsIgnoreCase(user.getRole());
    }

    public boolean isCustomer(User user) {
        return user != null && "CUSTOMER".equalsIgnoreCase(user.getRole());
    }

    public boolean isOwner(User user) {
        return user != null && "OWNER".equalsIgnoreCase(user.getRole());
    }

}

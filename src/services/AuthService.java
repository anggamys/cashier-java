package services;

import dao.AuthDao;
import models.User;

public class AuthService {
    private final AuthDao authDao = new AuthDao();

    public boolean registerUser(String username, String password, String role, String name, String email, String phone, String address) {
        if (authDao.isUsernameTaken(username)) {
            System.out.println("❌ Username is already taken!");
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);

        return authDao.registerUser(user);
    }

    public User login(String username, String password) {
        return authDao.login(username, password);
    }

    public boolean hasRole(User user, String role) {
        return user != null && role.equalsIgnoreCase(user.getRole());
    }
}

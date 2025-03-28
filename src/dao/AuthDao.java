package dao;

import org.mindrot.jbcrypt.BCrypt;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
    
    public void registerUser(User user) {
        String sql = "INSERT INTO users (username, password, role, name, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Hash password sebelum disimpan
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword); 
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getAddress());

            stmt.executeUpdate();
            System.out.println("✅ User registered successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");

                    // Verifikasi password dengan hashing
                    if (BCrypt.checkpw(password, storedHash)) {
                        user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address")
                        );
                        System.out.println("✅ Login successful! Role: " + user.getRole());
                    } else {
                        System.out.println("❌ Invalid password.");
                    }
                } else {
                    System.out.println("❌ User not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}

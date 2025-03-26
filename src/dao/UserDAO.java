package dao;

import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public void registerUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        // Hash password sebelum disimpan
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getRole());

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
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                // Verifikasi password dengan hashing
                if (BCrypt.checkpw(password, storedHash)) {
                    user = new User(rs.getInt("id"), rs.getString("username"), storedHash, rs.getString("role"));
                    System.out.println("✅ Login successful! Role: " + user.getRole());
                } else {
                    System.out.println("❌ Invalid password.");
                }
            } else {
                System.out.println("❌ User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}

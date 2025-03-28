package dao;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User[] getAllUsers(Connection conn) {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch users: " + e.getMessage());

        }
        return userList.toArray(new User[0]);
    }
}

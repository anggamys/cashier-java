package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Product;

public class ProductDao {

    public void addProduct(Product product, Connection conn) {
        String sql = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getPrice());
            stmt.setInt(4, product.getStock());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ Product added successfully: " + product.getName());
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to add product: " + e.getMessage());
        }
    }

    public Product[] getAllProducts(Connection conn) {
        String sql = "SELECT * FROM products";
        List<Product> productList = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    rs.getInt("stock")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch products: " + e.getMessage());
        }

        return productList.toArray(new Product[0]); 
    }

    public Product getProductById(int id, Connection conn) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); 
            
            if (rs.next()) { 
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    rs.getInt("stock")
                );
            } else {
                System.out.println("⚠️ Product not found with ID: " + id);
                return null;
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to fetch product: " + e.getMessage());
            return null;
        }
    }

    public void updateStockProduct(int productId, int quantities, Connection conn) {
        String sql = "UPDATE products SET stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantities);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("❌ Failed to update product stock: " + e.getMessage());
        }
    }
}
package services;

import dao.DatabaseConnection;
import dao.ProductDao;
import models.Product;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductService {
    private final ProductDao productDao;
    private final Connection connection;

    public ProductService() {
        this.connection = DatabaseConnection.getConnection(); 
        this.productDao = new ProductDao();
    }

    public boolean addProduct(String name, String category, int price, int stock) {
        try {
            Product product = new Product(0, name, category, price, stock);
            productDao.addProduct(product, connection);
            
            System.out.println("✅ Product added successfully: " + product.getName());        
            return true;
        } catch (Exception e) {
            System.out.println("❌ Failed to add product: " + e.getMessage());
            return false;
        }
    }
    
    public Product[] getAllProducts() {
        try (Connection conn = DatabaseConnection.getConnection()) { 
            return productDao.getAllProducts(conn);
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch products: " + e.getMessage());
            return new Product[0];
        }
    }


    public Product getProductById(int id) {
        try {
            return productDao.getProductById(id, connection);
        } catch (Exception e) {
            System.out.println("❌ Failed to fetch product: " + e.getMessage());
            return null;
        }
    }
}

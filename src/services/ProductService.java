package services;

import dao.DatabaseConnection;
import dao.ProductDao;
import dao.TransactionManager;
import models.Product;

import java.sql.SQLException;

public class ProductService {
    private final ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public boolean addProduct(String name, String category, int price, int stock) {
        try (TransactionManager tx = new TransactionManager(DatabaseConnection.getConnection())) {
            Product product = new Product(0, name, category, price, stock);
            productDao.addProduct(product, tx.getConnection());
            tx.commit(); 
            
            System.out.println("✅ Product added successfully: " + product.getName());
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Failed to add product: " + e.getMessage());
            return false;
        }
    }

    public Product[] getAllProducts() {
        try (TransactionManager tx = new TransactionManager(DatabaseConnection.getConnection())) {
            return productDao.getAllProducts(tx.getConnection());
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch products: " + e.getMessage());
            return new Product[0]; 
        }
    }

    public Product getProductById(int id) {
        try (TransactionManager tx = new TransactionManager(DatabaseConnection.getConnection())) {
            return productDao.getProductById(id, tx.getConnection());
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch product: " + e.getMessage());
            return null;
        }
    }
}

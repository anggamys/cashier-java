package service;

import model.*;
import dao.*;

public class ProductService {
    public boolean addProduct(String name, String category, int price, int stock) {
        ProductDao productDao = new ProductDao();
        
        Product product = new Product(0, name, category, price, stock); // ID diisi 0 karena akan digenerate oleh DB
    
        boolean success = productDao.addProduct(product);
        
        if (success) {
            System.out.println("✅ Product added successfully: " + product.getName());
        } else {
            System.out.println("❌ Failed to add product.");
        }
        
        return success;
    }

    public Product[] getAllProducts() {
        ProductDao productDao = new ProductDao();
        return productDao.getAllProducts();
    }

    public Product getProductById(int id) {
        ProductDao productDao = new ProductDao();
        return productDao.getProductById(id);
    }
}

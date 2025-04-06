package commands;

import models.Product;
import services.ProductService;

public class ProductUi {
    private final ProductService productService;

    public ProductUi(ProductService productService) { 
        this.productService = productService;
    }

    public void showAllProducts() {
        System.out.println("\n==== Product List ====");
        System.out.println("ID | Name         | Category    | Price  | Stock");
        System.out.println("------------------------------------------------");
    
        Product[] products = productService.getAllProducts(); 
    
        if (products.length == 0) {
            System.out.println("No products available.");
            return;
        }
    
        for (Product product : products) {
            System.out.printf("%-3d | %-12s | %-10s | %6d | %5d%n",
                product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.getStock());
        }
    }
    
}

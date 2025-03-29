package userinterfaces;

import java.util.Scanner;
import models.Product;
import models.Transaction;
import services.ProductService;
import services.TransactionService;
import utils.Ui;

public class CashierMenu {
    private final Scanner scanner;
    private final TransactionService transactionService;
    private final ProductUi productUi;
    private final ProductService productService;

    public CashierMenu(Scanner scanner, ProductUi productUi, TransactionService transactionService) { 
        this.scanner = scanner;
        this.productUi = productUi; 
        this.transactionService = transactionService;
        this.productService = new ProductService();
    }
    
    public void show() {
        while (true) {
            Ui.clearScreen();
            
            System.out.println("\n==== Cashier Menu ====");
            System.out.println("1. Process Transaction");
            System.out.println("0. Logout");
            int choice = Form.integerUserForm(scanner, "Choose an option: ");

            switch (choice) {
                case 1 -> processTransaction();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    private void processTransaction() {
        Ui.clearScreen();
        
        productUi.showAllProducts();

        String customerName = Form.stringUserForm(scanner, "Enter customer name: ");
        if (customerName.isEmpty()) {
            System.out.println("❌ Customer name cannot be empty!");
            return;
        }

        int numItems = Form.integerUserForm(scanner, "Enter number of items: ");
        if (numItems <= 0) {
            System.out.println("❌ The number of items must be at least 1.");
            return;
        }

        int[] productIds = new int[numItems];
        int[] quantities = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            while (true) {
                int productId = Form.integerUserForm(scanner, "Enter product ID for item " + (i + 1) + ": ");
                Product product = productService.getProductById(productId);

                if (product == null) {
                    System.out.println("❌ Product ID " + productId + " not found! Try again.");
                    continue;
                }

                int quantity = Form.integerUserForm(scanner, "Enter quantity for item " + (i + 1) + ": ");
                if (quantity <= 0) {
                    System.out.println("❌ Quantity must be greater than 0! Try again.");
                    continue;
                }

                if (product.getStock() < quantity) {
                    System.out.println("❌ Insufficient stock for product ID " + productId + "! Available: " + product.getStock());
                    continue;
                }

                productIds[i] = productId;
                quantities[i] = quantity;
                break;
            }
        }

        System.out.println("\nConfirm Transaction:");
        System.out.println("Customer Name: " + customerName);
        for (int i = 0; i < numItems; i++) {
            System.out.println(" - Product ID: " + productIds[i] + " | Quantity: " + quantities[i]);
        }

        String confirmation = Form.stringUserForm(scanner, "\nProceed with transaction? (yes/no): ").toLowerCase();
        if (!confirmation.equals("yes")) {
            System.out.println("❌ Transaction canceled.");
            return;
        }

        // Execute transaction
        var transactionIdOpt = transactionService.addTransaction(customerName, productIds, quantities);
        if (transactionIdOpt <= 0) {
            System.out.println("❌ Transaction failed. Please try again.");
            return;
        }

        int transactionId = transactionIdOpt;
        Transaction transaction = transactionService.getTransactionById(transactionId);

        if (transaction != null) {
            System.out.println("\n✅ Transaction Successful!");
            System.out.println("=====================================");
            System.out.println("Transaction ID   : " + transaction.getId());
            System.out.println("Customer Name    : " + transaction.getCustomerName());
            System.out.println("Date            : " + transaction.getDate());
            System.out.println("-------------------------------------");

            System.out.println("Items Purchased:");
            for (int i = 0; i < numItems; i++) {
                Product product = productService.getProductById(productIds[i]);
                if (product != null) {
                    System.out.printf(" - %-20s (ID: %d) x %d  =  %,d\n", 
                        product.getName(), productIds[i], quantities[i], product.getPrice() * quantities[i]);
                }
            }
            System.out.println("-------------------------------------");
            System.out.printf("Total Price      : %,d\n", transaction.getTotalPrice());
            System.out.println("=====================================");
        } else {
            System.out.println("❌ Transaction failed. Please try again.");
        }

        Ui.pressEnterToContinue();
    }
}

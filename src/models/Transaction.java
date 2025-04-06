package models;

import java.time.LocalDateTime;

public class Transaction {

    private final String id;
    private final String customerName;
    private final int totalAmount;
    private final LocalDateTime date;

    public Transaction(String id, String customerName, int totalAmount, LocalDateTime date) {
        this.id = id;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.date = date != null ? date : LocalDateTime.now(); // fallback jika null
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int setTotalAmount(int totalAmount) {
        return this.totalAmount;
    }
}

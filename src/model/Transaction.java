package model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private String customerName;
    private int totalPrice;
    private LocalDateTime date; 
    
    public Transaction() {}

    public Transaction(int id, String customerName, int totalPrice, LocalDateTime date) {
        this.id = id;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                '}';
    }
}

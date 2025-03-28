package model;

public class ItemTransaction {
    private int id;
    private int transactionId;
    private int productId;
    private int quantity;
    private int amount;

    public ItemTransaction() {}

    public ItemTransaction(int id, int transactionId, int productId, int quantity, int pricePerItem) {
        this.id = id;
        this.transactionId = transactionId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = quantity * pricePerItem; // 🔥 Hitung otomatis
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ItemTransaction{" +
                "id=" + id +
                ", transactionId=" + transactionId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}

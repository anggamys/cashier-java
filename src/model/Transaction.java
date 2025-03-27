package model;

public class Transaction {
    private int id;
    private String customerName;
    private int totalPrice;
    private String date;

    public Transaction() {}

    public Transaction(int id, String customerName, int totalPrice, String date) {
        this.id = id;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.date = date;
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

    public int getTotalPrice() {  // 🔹 Konsisten dengan nama atribut
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {  // 🔹 Konsisten dengan getter
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", totalPrice=" + totalPrice +
                ", date='" + date + '\'' +
                '}';
    }
}

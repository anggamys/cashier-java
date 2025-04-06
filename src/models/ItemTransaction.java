package models;

public class ItemTransaction {

    private final String id;
    private final String transactionId;
    private final String itemId;
    private final int quantity;
    private final int subTotal;

    public ItemTransaction(String id, String transactionId, String itemId, int quantity, int subTotal) {
        this.id = id;
        this.transactionId = transactionId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public String getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubTotal() {
        return subTotal;
    }
}

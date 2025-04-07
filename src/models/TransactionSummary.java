package models;

public class TransactionSummary {
    private int totalTransactions;
    private int totalIncome;
    private Transaction latestTransaction;

    public TransactionSummary(int totalTransactions, int totalIncome, Transaction latestTransaction) {
        this.totalTransactions = totalTransactions;
        this.totalIncome = totalIncome;
        this.latestTransaction = latestTransaction;
    }

    // Getter
    public int getTotalTransactions() { return totalTransactions; }
    public int getTotalIncome() { return totalIncome; }
    public Transaction getLatestTransaction() { return latestTransaction; }
}

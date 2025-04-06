package repository;

import java.sql.*;

import models.*;
import utils.*;

public class ItemTransactionRepo {

    private static final String ADD_ITEM_TRANSACTION = 
        "INSERT INTO item_transaction (id, item_id, transaction_id, quantity, sub_total) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ITEM_TRANSACTION_BY_ID = 
        "SELECT * FROM item_transaction WHERE id = ?";

    public ItemTransaction addItemTransaction(final ItemTransaction itemTransaction) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_ITEM_TRANSACTION)) {

            stmt.setString(1, itemTransaction.getId());
            stmt.setString(2, itemTransaction.getItemId());
            stmt.setString(3, itemTransaction.getTransactionId());
            stmt.setInt(4, itemTransaction.getQuantity());
            stmt.setInt(5, itemTransaction.getSubTotal());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                return itemTransaction;
            } else {
                System.out.println("❌ Failed to add item transaction.");
                return null;
            }

        } catch (SQLException e) {
            FormatUtil.logError("ItemTransactionRepo", "addItemTransaction", e);
            return null;
        }
    }

    public ItemTransaction getItemTransactionById(final String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ITEM_TRANSACTION_BY_ID)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToItemTransaction(rs);
                } else {
                    System.out.println("❌ Item transaction not found.");
                    return null;
                }
            }

        } catch (SQLException e) {
            FormatUtil.logError("ItemTransactionRepo", "getItemTransactionById", e);
            return null;
        }
    }

    private ItemTransaction mapResultSetToItemTransaction(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String itemId = rs.getString("item_id");
        String transactionId = rs.getString("transaction_id");
        int quantity = rs.getInt("quantity");
        int subTotal = rs.getInt("sub_total");

        return new ItemTransaction(id, transactionId, itemId, quantity, subTotal);
    }
}

package repository;

import java.sql.*;
import java.util.*;

import models.*;
import utils.*;

public class ItemTransactionRepo {

    private static final String ADD_ITEM_TRANSACTION = 
        "INSERT INTO item_transaction (id, item_id, transaction_id, quantity, sub_total) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_ITEM_TRANSACTION_BY_ID = 
        "SELECT * FROM item_transaction WHERE id = ?";

    private static final String GET_ITEM_TRANSACTIONS_BY_TRANSACTION_ID = 
        "SELECT * FROM item_transaction WHERE transaction_id = ?";

    private static final String UPDATE_ITEM_TRANSACTION =
        "UPDATE item_transaction SET item_id = ?, transaction_id = ?, quantity = ?, sub_total = ? WHERE id = ?";

    private static final String DELETE_ITEM_TRANSACTION_BY_ID =
        "DELETE FROM item_transaction WHERE id = ?";

    public ItemTransaction addItemTransaction(final ItemTransaction itemTransaction) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_ITEM_TRANSACTION)) {

            stmt.setString(1, itemTransaction.getId());
            stmt.setString(2, itemTransaction.getItemId());
            stmt.setString(3, itemTransaction.getTransactionId());
            stmt.setInt(4, itemTransaction.getQuantity());
            stmt.setInt(5, itemTransaction.getSubTotal());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0 ? itemTransaction : null;

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

    public ItemTransaction[] getItemTransactionsByTransactionId(String transactionId) {
        ArrayList<ItemTransaction> items = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ITEM_TRANSACTIONS_BY_TRANSACTION_ID)) {

            stmt.setString(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToItemTransaction(rs));
                }
            }

        } catch (SQLException e) {
            FormatUtil.logError("ItemTransactionRepo", "getItemTransactionsByTransactionId", e);
        }

        return items.toArray(new ItemTransaction[0]);
    }

    public boolean updateItemTransaction(final ItemTransaction itemTransaction) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_ITEM_TRANSACTION)) {

            stmt.setString(1, itemTransaction.getItemId());
            stmt.setString(2, itemTransaction.getTransactionId());
            stmt.setInt(3, itemTransaction.getQuantity());
            stmt.setInt(4, itemTransaction.getSubTotal());
            stmt.setString(5, itemTransaction.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            FormatUtil.logError("ItemTransactionRepo", "updateItemTransaction", e);
            return false;
        }
    }

    public boolean deleteItemTransactionById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_ITEM_TRANSACTION_BY_ID)) {

            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            FormatUtil.logError("ItemTransactionRepo", "deleteItemTransactionById", e);
            return false;
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

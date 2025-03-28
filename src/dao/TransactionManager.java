package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final Connection conn;

    public TransactionManager(Connection conn) {
        this.conn = conn;
    }

    public void beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

package repository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager implements AutoCloseable {
    private final Connection conn;
    private boolean committed = false;

    public TransactionManager(Connection conn) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.conn = conn;
        this.conn.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        if (!committed) {
            conn.commit();
            committed = true;
        }
    }

    public void rollback() throws SQLException {
        if (!committed) {
            conn.rollback();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    @Override
    public void close() throws SQLException {
        try {
            if (!committed) {
                rollback(); 
            }
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

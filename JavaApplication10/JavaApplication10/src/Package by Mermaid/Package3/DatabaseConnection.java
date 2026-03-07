package Package3;

import java.sql.*;

/**
 * Database Connection Manager for MS SQL Server - Singleton Pattern
 */
public class DatabaseConnection {

    private static final String SERVER_NAME = "DESKTOP-P61KLBK\\SQLEXPRESS";
    private static final String DB_NAME = "Hospital_Management_System";

    // غيرنا سلسلة الاتصال لـ SQL Server Authentication
    private static final String DB_URL = "jdbc:sqlserver://" + SERVER_NAME
            + ";databaseName=" + DB_NAME
            + ";user=sa;password=123456"
            + ";trustServerCertificate=true;";

    // ==================== Singleton ====================

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL);
            connection.setAutoCommit(true);
            System.out.println("✓ Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ SQL Server JDBC Driver not found!");
            throw new SQLException("JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("✗ Database connection failed: " + e.getMessage());
            throw e;
        }
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // ==================== Operations ====================

    public Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
                instance = null;
                System.out.println("✓ Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
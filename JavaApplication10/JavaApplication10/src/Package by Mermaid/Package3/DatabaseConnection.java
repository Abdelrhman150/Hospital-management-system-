package Package3;

import java.sql.*;

/**
 * Database Connection Manager for MS SQL Server
 * Manages database connections using Singleton pattern
 */

public class DatabaseConnection {

    private static final String SERVER_NAME = "LAPTOP-OUGK83B0\\SQLEXPRESS";
    private static final String DB_NAME = "hospital_mangament_system";

    private static final String DB_URL = "jdbc:sqlserver://" + SERVER_NAME +
            ";databaseName=" + DB_NAME +
            ";integratedSecurity=true;trustServerCertificate=true;";

    private static Connection connection = null;
    private static DatabaseConnection instance = null;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get database connection (Singleton pattern)
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                connection = DriverManager.getConnection(DB_URL);
                System.out.println("✓ Database connected successfully!");

            } catch (ClassNotFoundException e) {
                System.err.println("✗ SQL Server JDBC Driver not found!");
                System.err.println("  Please make sure the 'mssql-jdbc' JAR file is added to the project's Libraries.");
                throw new SQLException("JDBC Driver not found", e);
            } catch (SQLException e) {
                System.err.println("✗ Database connection failed!");
                System.err.println("  Error: " + e.getMessage());
                if (e.getMessage().contains("integrated security")) {
                    System.err.println(
                            "  Hint: Did you copy the 'mssql-jdbc_auth' DLL file to your project's main folder?");
                }
                if (e.getMessage().contains("SQL Server Browser")) {
                    System.err.println(
                            "  Hint: Is the 'SQL Server Browser' service running? Check SQL Server Configuration Manager.");
                }
                throw e;
            }
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✓ Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        System.out.println("Testing Database Connection to SQL Server...");
        Connection conn = null;
        try {
            conn = getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("--- Connection Info ---");
            System.out.println("  Database: " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion());
            System.out.println("  URL: " + meta.getURL());
            System.out.println("-----------------------");

        } catch (SQLException e) {
            System.out.println("\n❌ Connection Test Failed.");

        } finally {
            if (conn != null) {
                closeConnection();
            }
        }
    }
}

package dao;

import java.sql.*;

/**
 * Database Connection Manager for MS SQL Server
 */
public class DatabaseConnection {

    private static final String SERVER_NAME = "LAPTOP-OUGK83B0\\SQLEXPRESS";
    private static final String DB_NAME = "hospital_db";

    private static final String DB_URL = "jdbc:sqlserver://" + SERVER_NAME +
            ";databaseName=" + DB_NAME +
            ";integratedSecurity=true;trustServerCertificate=true;";

    private static Connection connection = null;

    /**
     * Get database connection (Singleton pattern)
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("✓ Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("✗ SQL Server JDBC Driver not found!");
                throw new SQLException("JDBC Driver not found", e);
            } catch (SQLException e) {
                System.err.println("✗ Database connection failed!");
                throw e;
            }
        }
        return connection;
    }

    /**
     * Initialize the rooms table if it doesn't exist
     */
    public static void initializeDatabase() {
        String createRoomTableSQL = "IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[rooms]') AND type in (N'U')) "
                + "BEGIN "
                + "CREATE TABLE [dbo].[rooms] ("
                + "[roomId] [nvarchar](50) PRIMARY KEY,"
                + "[roomType] [nvarchar](50) NOT NULL,"
                + "[capacity] [int] NOT NULL,"
                + "[availabilityStatus] [nvarchar](50) NOT NULL,"
                + "[dailyRate] [decimal](10, 2) NOT NULL"
                + "); "
                + "END";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(createRoomTableSQL);
            System.out.println("✓ Table 'rooms' initialized successfully!");
        } catch (SQLException e) {
            System.err.println("✗ Error initializing table: " + e.getMessage());
        }
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
}

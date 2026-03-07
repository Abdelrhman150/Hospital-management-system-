package Package3;

import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connecter;

    private DatabaseConnection() throws Exception {
        connecter = DriverManager.getConnection(
                "jdbc:sqlserver://DESKTOP-P61KLBK\\SQLEXPRESS:1433;databaseName=Hospital_Management_System;integratedSecurity=true;trustServerCertificate=true;");
    }

    public static DatabaseConnection getInstance() throws Exception {
        if (instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    public Connection getConnection() {
        return connecter;
    }
}

package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton Room ID Generator that fetches the max ID from the database
 */
public class RoomIdGenerator {

    private static RoomIdGenerator instance;

    private RoomIdGenerator() {
        // Private constructor to prevent instantiation
    }

    public static synchronized RoomIdGenerator getInstance() {
        if (instance == null) {
            instance = new RoomIdGenerator();
        }
        return instance;
    }

    public String generateRoomId() {
        String query = "SELECT MAX(roomId) AS maxId FROM rooms WHERE roomId LIKE 'ROOM%'";
        String newId = "ROOM001";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                String maxId = rs.getString("maxId");
                if (maxId != null && maxId.startsWith("ROOM")) {
                    try {
                        // Extract number from "ROOMxxx"
                        int currentNumber = Integer.parseInt(maxId.substring(4));
                        newId = String.format("ROOM%03d", currentNumber + 1);
                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Could not parse max room ID: " + maxId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating room ID: " + e.getMessage());
        }
        return newId;
    }
}

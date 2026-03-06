package Package3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Room database operations.
 * Uses a simple RoomRecord inner class for database row representation,
 * separate from the Package1.Room interface used in the Factory Pattern.
 */
public class RoomDAO {

    /**
     * Simple data holder for a room database record.
     */
    public static class RoomRecord {
        private String roomId;
        private String roomType;
        private int capacity;
        private String availabilityStatus;
        private double dailyRate;

        public RoomRecord(String roomId, String roomType, int capacity, String availabilityStatus, double dailyRate) {
            this.roomId = roomId;
            this.roomType = roomType;
            this.capacity = capacity;
            this.availabilityStatus = availabilityStatus;
            this.dailyRate = dailyRate;
        }

        public String getRoomId() {
            return roomId;
        }

        public String getRoomType() {
            return roomType;
        }

        public int getCapacity() {
            return capacity;
        }

        public String getAvailabilityStatus() {
            return availabilityStatus;
        }

        public double getDailyRate() {
            return dailyRate;
        }
    }

    public RoomDAO() {
        DatabaseConnection.initializeDatabase();
    }

    public void addRoom(RoomRecord room) throws SQLException {
        String sql = "INSERT INTO rooms(roomId, roomType, capacity, availabilityStatus, dailyRate) VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomId());
            pstmt.setString(2, room.getRoomType());
            pstmt.setInt(3, room.getCapacity());
            pstmt.setString(4, room.getAvailabilityStatus());
            pstmt.setDouble(5, room.getDailyRate());
            pstmt.executeUpdate();
        }
    }

    public List<RoomRecord> getAllRooms() throws SQLException {
        List<RoomRecord> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(new RoomRecord(
                        rs.getString("roomId"),
                        rs.getString("roomType"),
                        rs.getInt("capacity"),
                        rs.getString("availabilityStatus"),
                        rs.getDouble("dailyRate")));
            }
        }
        return rooms;
    }

    public void updateRoom(RoomRecord room) throws SQLException {
        String sql = "UPDATE rooms SET roomType = ?, capacity = ?, availabilityStatus = ?, dailyRate = ? WHERE roomId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomType());
            pstmt.setInt(2, room.getCapacity());
            pstmt.setString(3, room.getAvailabilityStatus());
            pstmt.setDouble(4, room.getDailyRate());
            pstmt.setString(5, room.getRoomId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating room failed, no rows affected for ID: " + room.getRoomId());
            }
        }
    }

    public void deleteRoom(String roomId) throws SQLException {
        String sql = "DELETE FROM rooms WHERE roomId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roomId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting room failed, no rows affected for ID: " + roomId);
            }
        }
    }
}

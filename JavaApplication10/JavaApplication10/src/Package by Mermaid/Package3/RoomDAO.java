package dao;

import entity.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public RoomDAO() {
        DatabaseConnection.initializeDatabase();
    }

    public void addRoom(Room room) throws SQLException {
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

    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getString("roomId"),
                        rs.getString("roomType"),
                        rs.getInt("capacity"),
                        rs.getString("availabilityStatus"),
                        rs.getDouble("dailyRate")));
            }
        }
        return rooms;
    }

    public void updateRoom(Room room) throws SQLException {
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

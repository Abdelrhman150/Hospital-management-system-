package Package3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // ==================== Singleton ====================

    private static RoomDAO instance;

    private RoomDAO() {
    }

    public static synchronized RoomDAO getInstance() {
        if (instance == null)
            instance = new RoomDAO();
        return instance;
    }

    // ==================== Room Record ====================

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

    // ==================== ID Generator ====================

    public String generateRoomId() {
        String query = "SELECT MAX(roomId) AS maxId FROM rooms WHERE roomId LIKE 'ROOM%'";
        String newId = "ROOM001";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String maxId = rs.getString("maxId");
                if (maxId != null && maxId.startsWith("ROOM")) {
                    int num = Integer.parseInt(maxId.substring(4));
                    newId = String.format("ROOM%03d", num + 1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error generating room ID: " + e.getMessage());
        }
        return newId;
    }

    // ==================== CRUD Operations ====================

    public void addRoom(RoomRecord room) throws Exception {
        String sql = "INSERT INTO rooms(roomId, roomType, capacity, availabilityStatus, dailyRate) VALUES(?,?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomId());
            ps.setString(2, room.getRoomType());
            ps.setInt(3, room.getCapacity());
            ps.setString(4, room.getAvailabilityStatus());
            ps.setDouble(5, room.getDailyRate());
            ps.executeUpdate();
        }
    }

    public List<RoomRecord> getAllRooms() throws Exception {
        List<RoomRecord> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (Statement stmt = conn.createStatement();
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

    public void updateRoom(RoomRecord room) throws Exception {
        String sql = "UPDATE rooms SET roomType=?, capacity=?, availabilityStatus=?, dailyRate=? WHERE roomId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomType());
            ps.setInt(2, room.getCapacity());
            ps.setString(3, room.getAvailabilityStatus());
            ps.setDouble(4, room.getDailyRate());
            ps.setString(5, room.getRoomId());
            if (ps.executeUpdate() == 0)
                throw new SQLException("No room found with ID: " + room.getRoomId());
        }
    }

    public void deleteRoom(String roomId) throws Exception {
        String sql = "DELETE FROM rooms WHERE roomId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No room found with ID: " + roomId);
        }
    }

    public void markRoomOccupied(String roomId) throws Exception {
        String sql = "UPDATE rooms SET availabilityStatus='Occupied' WHERE roomId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No room found with ID: " + roomId);
        }
    }

    public void markRoomAvailable(String roomId) throws Exception {
        String sql = "UPDATE rooms SET availabilityStatus='Available' WHERE roomId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomId);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No room found with ID: " + roomId);
        }
    }
}

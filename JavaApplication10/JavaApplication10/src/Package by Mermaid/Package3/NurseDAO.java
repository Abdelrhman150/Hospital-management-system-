package Package3;

import java.sql.*;

public class NurseDAO {

    // ==================== Singleton ====================

    private static NurseDAO instance;

    private NurseDAO() {
    }

    public static synchronized NurseDAO getInstance() {
        if (instance == null)
            instance = new NurseDAO();
        return instance;
    }

    // ==================== ID Generator ====================

    public int generateNurseId() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ISNULL(MAX(nurseId), 0) + 1 FROM Nurses");
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            System.err.println("Error generating nurse ID: " + e.getMessage());
        }
        return 1;
    }

    // ==================== Operations ====================

    public void addNurse(int nurseId, String name, String phone,
            String shift, String availability) throws Exception {
        String sql = "INSERT INTO Nurses(nurseId, name, phone, shift, availability) VALUES(?,?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nurseId);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, shift);
            ps.setString(5, availability);
            ps.executeUpdate();
        }
    }

    public void updateNurse(int nurseId, String name, String phone, String shift) throws Exception {
        String sql = "UPDATE Nurses SET name=?, phone=?, shift=? WHERE nurseId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, shift);
            ps.setInt(4, nurseId);
            ps.executeUpdate();
        }
    }

    public void deleteNurse(int nurseId) throws Exception {
        String sql = "DELETE FROM Nurses WHERE nurseId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nurseId);
            ps.executeUpdate();
        }
    }

    public void setAvailability(int nurseId, String availability) throws Exception {
        String sql = "UPDATE Nurses SET availability=? WHERE nurseId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, availability);
            ps.setInt(2, nurseId);
            ps.executeUpdate();
        }
    }

    public ResultSet getAssignedShift(int nurseId) throws Exception {
        String sql = "SELECT shift FROM Nurses WHERE nurseId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, nurseId);
        return ps.executeQuery();
    }

    public ResultSet getAvailableNurses() throws Exception {
        String sql = "SELECT * FROM Nurses WHERE availability='Available'";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        return conn.createStatement().executeQuery(sql);
    }
}

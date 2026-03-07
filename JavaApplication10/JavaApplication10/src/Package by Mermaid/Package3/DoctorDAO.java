package Package3;

import java.sql.*;

public class DoctorDAO {

    // ==================== Singleton ====================

    private static DoctorDAO instance;

    private DoctorDAO() {
    }

    public static synchronized DoctorDAO getInstance() {
        if (instance == null)
            instance = new DoctorDAO();
        return instance;
    }

    // ==================== ID Generator ====================

    public int generateDoctorId() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT ISNULL(MAX(doctorId), 0) + 1 FROM Doctors");
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            System.err.println("Error generating doctor ID: " + e.getMessage());
        }
        return 1;
    }

    // ==================== Operations ====================

    public void addDoctor(int doctorId, String name, String specialization,
            String phone, int departmentId) throws Exception {
        String sql = "INSERT INTO Doctors(doctorId, name, specialization, phone, departmentId) VALUES(?,?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setString(2, name);
            ps.setString(3, specialization);
            ps.setString(4, phone);
            ps.setInt(5, departmentId);
            ps.executeUpdate();
        }
    }

    public void updateDoctor(int doctorId, String name, String specialization, String phone) throws Exception {
        String sql = "UPDATE Doctors SET name=?, specialization=?, phone=? WHERE doctorId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, phone);
            ps.setInt(4, doctorId);
            ps.executeUpdate();
        }
    }

    public void deleteDoctor(int doctorId) throws Exception {
        String sql = "DELETE FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.executeUpdate();
        }
    }

    public ResultSet getAllDoctors() throws Exception {
        String sql = "SELECT * FROM Doctors";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        return conn.createStatement().executeQuery(sql);
    }

    public ResultSet getDoctorById(int doctorId) throws Exception {
        String sql = "SELECT * FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, doctorId);
        return ps.executeQuery();
    }
}

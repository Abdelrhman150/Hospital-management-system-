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

    // ==================== Operations ====================

    public void addDoctor(int doctorId, String name, String specialization,
            String contactEmail, double consultationFee) throws Exception {
        String sql = "INSERT INTO Doctors(doctorId, name, specialization, contactEmail, consultationFee) VALUES(?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setString(2, name);
            ps.setString(3, specialization);
            ps.setString(4, contactEmail);
            ps.setDouble(5, consultationFee);
            ps.executeUpdate();
        }
    }

    public void updateDoctor(int doctorId, String name, String specialization,
            String contactEmail, double consultationFee) throws Exception {
        String sql = "UPDATE Doctors SET name=?, specialization=?, contactEmail=?, consultationFee=? WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, contactEmail);
            ps.setDouble(4, consultationFee);
            ps.setInt(5, doctorId);
            ps.executeUpdate();
        }
    }

    public void deleteDoctor(int doctorId) throws Exception {
        String sql = "DELETE FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.executeUpdate();
        }
    }

    public ResultSet getAllDoctors() throws Exception {
        String sql = "SELECT * FROM Doctors";
        Connection conn = DatabaseConnection.getConnection();
        return conn.createStatement().executeQuery(sql);
    }

    public ResultSet getDoctorById(int doctorId) throws Exception {
        String sql = "SELECT * FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, doctorId);
        return ps.executeQuery();
    }

    public ResultSet getAvailableDoctors() throws SQLException{
        String sql = "SELECT * FROM Doctors WHERE AvailabilityStatus = 'Available'" ;
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }
}

package Package3;

import java.sql.*;

public class PatientDAO {

    // ==================== Singleton ====================

    private static PatientDAO instance;

    private PatientDAO() {
    }

    public static synchronized PatientDAO getInstance() {
        if (instance == null)
            instance = new PatientDAO();
        return instance;
    }

    // ==================== Operations ====================

    public void addPatient(int patientId, String name, String gender, String bloodType,
            String phone, String address, Date dateOfBirth) throws Exception {
        String sql = "INSERT INTO Patients(patientId, name, gender, bloodType, phone, address, dateOfBirth) VALUES(?,?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setString(4, bloodType);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setDate(7, dateOfBirth);
            ps.executeUpdate();
        }
    }

    public void updatePatient(int patientId, String name, String phone, String address) throws Exception {
        String sql = "UPDATE Patients SET name=?, phone=?, address=? WHERE patientId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setInt(4, patientId);
            ps.executeUpdate();
        }
    }

    public void deletePatient(int patientId) throws Exception {
        String sql = "DELETE FROM Patients WHERE patientId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            ps.executeUpdate();
        }
    }

    public ResultSet searchPatient(String name) throws Exception {
        String sql = "SELECT * FROM Patients WHERE name LIKE ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        return ps.executeQuery();
    }

    public ResultSet getPatientById(int patientId) throws Exception {
        String sql = "SELECT * FROM Patients WHERE patientId=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        return ps.executeQuery();
    }
}

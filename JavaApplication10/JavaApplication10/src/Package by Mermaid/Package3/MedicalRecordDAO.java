package Package3;

import java.sql.*;

public class MedicalRecordDAO {

    // ==================== Singleton ====================

    private static MedicalRecordDAO instance;

    private MedicalRecordDAO() {
    }

    public static synchronized MedicalRecordDAO getInstance() {
        if (instance == null)
            instance = new MedicalRecordDAO();
        return instance;
    }

    // ==================== Operations ====================

    public void createMedicalRecord(int recordId, int patientId, int doctorId,
            String diagnosis, String complaint, Date recordDate) throws Exception {
        String sql = "INSERT INTO MedicalRecords(recordId, patientId, doctorId, diagnosis, complaint, recordDate) VALUES(?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, recordId);
            ps.setInt(2, patientId);
            ps.setInt(3, doctorId);
            ps.setString(4, diagnosis);
            ps.setString(5, complaint);
            ps.setDate(6, recordDate);
            ps.executeUpdate();
        }
    }

    public void updateMedicalRecord(int recordId, String diagnosis, String complaint) throws Exception {
        String sql = "UPDATE MedicalRecords SET diagnosis=?, complaint=? WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, diagnosis);
            ps.setString(2, complaint);
            ps.setInt(3, recordId);
            ps.executeUpdate();
        }
    }

    public ResultSet getPatientHistory(int patientId) throws Exception {
        String sql = "SELECT * FROM MedicalRecords WHERE patientId=? ORDER BY recordDate DESC";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        return ps.executeQuery();
    }
}

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

    public void createMedicalRecord(String patientId, String doctorId,
            String diagnosis, String complaint, Date recordDate) throws Exception {
        String sql = "INSERT INTO MedicalRecords(recordId, patientId, doctorId, diagnosis, complaint, recordDate) VALUES(?,?,?,?,?,?)";
        
        // Get next recordId automatically from IdGenerator
        String nextRecordId = Package2.IdGenerator.getInstance().nextRecordId();
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nextRecordId);
            ps.setString(2, patientId);
            ps.setString(3, doctorId);
            ps.setString(4, diagnosis);
            ps.setString(5, complaint);
            ps.setDate(6, recordDate);
            ps.executeUpdate();
        }
    }

    public void updateMedicalRecord(String recordId, String diagnosis, String complaint) throws Exception {
        String sql = "UPDATE MedicalRecords SET diagnosis=?, complaint=? WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, diagnosis);
            ps.setString(2, complaint);
            ps.setString(3, recordId);
            ps.executeUpdate();
        }
    }

    public ResultSet getPatientHistory(String patientId) throws Exception {
        String sql = "SELECT * FROM MedicalRecords WHERE patientId=? ORDER BY recordDate DESC";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, patientId);
        return ps.executeQuery();
    }

    // ==================== Decorator Methods ====================

    public void addLabResult(String recordId, String labResult) throws Exception {
        String sql = "UPDATE MedicalRecords SET labResults=? WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, labResult);
            ps.setString(2, recordId);
            ps.executeUpdate();
        }
    }

    public void addXRayScan(String recordId, String scanDetails) throws Exception {
        String sql = "UPDATE MedicalRecords SET xrayScan=? WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, scanDetails);
            ps.setString(2, recordId);
            ps.executeUpdate();
        }
    }

    public void addAllergyWarning(String recordId, String allergyWarning) throws Exception {
        String sql = "UPDATE MedicalRecords SET allergyWarning=? WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, allergyWarning);
            ps.setString(2, recordId);
            ps.executeUpdate();
        }
    }

    public ResultSet getFullRecord(String recordId) throws Exception {
        String sql = "SELECT * FROM MedicalRecords WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, recordId);
        return ps.executeQuery();
    }
}

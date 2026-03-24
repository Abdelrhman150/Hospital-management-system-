package Package3;

import java.sql.*;
import Package1.MedicalRecord;

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

    public void createMedicalRecord(int patientId, int doctorId,
            String diagnosis, String complaint, Date recordDate) throws Exception {
        String sql = "INSERT INTO MedicalRecords(recordId, patientId, doctorId, diagnosis, complaint, recordDate) VALUES(?,?,?,?,?,?)";
        
        // Get next recordId automatically
        int nextRecordId = 1;
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT ISNULL(MAX(recordId), 0) + 1 FROM MedicalRecords")) {
            if (rs.next()) nextRecordId = rs.getInt(1);
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nextRecordId);
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

  

   

    public ResultSet getFullRecord(int recordId) throws Exception {
        String sql = "SELECT * FROM MedicalRecords WHERE recordId=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, recordId);
        return ps.executeQuery();
    }

    /**
     * Helper method to convert ResultSet to MedicalRecord object
     */
    public MedicalRecord mapToModel(ResultSet rs) throws SQLException {
        MedicalRecord record = new MedicalRecord();
        record.recordId = rs.getInt("recordId");
        record.patientId = rs.getInt("patientId");
        record.doctorId = rs.getInt("doctorId");
        record.dateCreated = rs.getDate("recordDate");
        record.chiefComplaint = rs.getString("complaint");
        record.clinicalDiagnosis = rs.getString("diagnosis");
        // appointmentId might be missing in some schemas, let's keep it optional
        return record;
    }
}

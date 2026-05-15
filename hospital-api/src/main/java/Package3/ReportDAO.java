package Package3;

import java.sql.*;

/**
 * Data Access Object for Reports.
 * Handles database operations for fetching data needed by reports.
 */
public class ReportDAO {

    private static ReportDAO instance;

    private ReportDAO() {}

    public static synchronized ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

  
    public String getPatientHistoryData(String patientId) throws Exception {
        String sql = "SELECT p.name, mr.diagnosis, mr.treatment, mr.visitDate " +
                     "FROM Patients p " +
                     "JOIN MedicalRecords mr ON p.patientId = mr.patientId " +
                     "WHERE p.patientId = ? " +
                     "ORDER BY mr.visitDate DESC";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder data = new StringBuilder();
                boolean found = false;
                
                
                while (rs.next()) {
                    if (!found) {
                        data.append("Patient History for: ").append(rs.getString("name")).append("\n");
                        data.append("========================================\n");
                        found = true;
                    }
                    data.append("- Visit Date: ").append(rs.getDate("visitDate")).append("\n");
                    data.append("  Diagnosis: ").append(rs.getString("diagnosis")).append("\n");
                    data.append("  Treatment: ").append(rs.getString("treatment")).append("\n");
                    data.append("----------------------------------------\n");
                }
                
                if (!found) {
                    return "No medical records found for Patient ID: " + patientId;
                }
                
                return data.toString();
            }
        }
    }

  
    public String[] getLatestPatientInfo(String patientId) throws Exception {
        
        String sql = "SELECT TOP 1 p.name, mr.diagnosis " +
                     "FROM Patients p " +
                     "LEFT JOIN MedicalRecords mr ON p.patientId = mr.patientId " +
                     "WHERE p.patientId = ? " +
                     "ORDER BY mr.visitDate DESC";
        
       
       
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{rs.getString("name"), rs.getString("diagnosis")};
                }
                return new String[]{"Unknown", "No Diagnosis Found"};
            }
        }
    }
//(bills)بتجيب الفواتير من الداتا سيت

    public double[] getFinancialSummaryDataMetrics() throws Exception {
        String sql = "SELECT SUM(amount) as total_revenue FROM Bills";
        
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double revenue = rs.getDouble("total_revenue");
                    double expenses = revenue * 0.6; 
                    return new double[]{revenue, expenses};
                }
                return new double[]{0.0, 0.0};
            }
        }
    }
}

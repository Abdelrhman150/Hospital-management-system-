package Package3;

import java.sql.*;

public class SecretaryDAO {

    // ==================== Singleton ====================

    private static SecretaryDAO instance;

    private SecretaryDAO() {
    }

    public static synchronized SecretaryDAO getInstance() {
        if (instance == null)
            instance = new SecretaryDAO();
        return instance;
    }

    // ==================== Operations ====================

    public void addSecretary(String secretaryId, String name, String phone,
            String email, String shift, String departmentId) throws Exception {
        String sql = "INSERT INTO Secretaries(secretaryId, name, phone, email, shift, departmentId) VALUES(?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, secretaryId);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.setString(5, shift);
            ps.setString(6, departmentId);

            ps.executeUpdate();
        }
    }

    public void updateSecretary(String secretaryId, String name, String phone) throws Exception {
        String sql = "UPDATE Secretaries SET name=?, phone=? WHERE secretaryId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, secretaryId);
            ps.executeUpdate();
        }
    }

    public void deleteSecretary(String secretaryId) throws Exception {
        String sql = "DELETE FROM Secretaries WHERE secretaryId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, secretaryId);
            ps.executeUpdate();
        }
    }

    public ResultSet getAllSecretaries() throws Exception {
        String sql = "SELECT * FROM Secretaries";
        Connection conn = DatabaseConnection.getConnection();
        return conn.createStatement().executeQuery(sql);
    }
}

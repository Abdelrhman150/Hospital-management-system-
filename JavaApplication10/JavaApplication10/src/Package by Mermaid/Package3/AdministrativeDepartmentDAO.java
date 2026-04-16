package Package3;

import java.sql.*;

public class AdministrativeDepartmentDAO {

    // ==================== Singleton ====================

    private static AdministrativeDepartmentDAO instance;

    private AdministrativeDepartmentDAO() {
    }

    public static synchronized AdministrativeDepartmentDAO getInstance() {
        if (instance == null)
            instance = new AdministrativeDepartmentDAO();
        return instance;
    }

    // ==================== Operations ====================

    public void addDepartment(String departmentId, String name) throws Exception {
        String sql = "INSERT INTO AdministrativeDepartment (departmentId, name) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId);
            ps.setString(2, name);
            ps.executeUpdate();
        }
    }

    public void updateDepartment(String departmentId, String name) throws Exception {
        String sql = "UPDATE AdministrativeDepartment SET name=? WHERE departmentId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, departmentId);
            ps.executeUpdate();
        }
    }

    public void deleteDepartment(String departmentId) throws Exception {
        String sql = "DELETE FROM AdministrativeDepartment WHERE departmentId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId);
            ps.executeUpdate();
        }
    }

    public ResultSet getAllDepartments() throws Exception {
        String sql = "SELECT * FROM AdministrativeDepartment";
        Connection conn = DatabaseConnection.getConnection();

        return conn.createStatement().executeQuery(sql);
    }

    public ResultSet getDepartmentById(String departmentId) throws Exception {
        String sql = "SELECT * FROM AdministrativeDepartment WHERE departmentId = ?";
        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, departmentId);

        return ps.executeQuery();
    }
}
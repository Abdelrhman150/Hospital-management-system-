package Package3;

import java.sql.*;

public class DepartmentDAO {

    // ==================== Singleton ====================

    private static DepartmentDAO instance;

    private DepartmentDAO() {
    }

    public static synchronized DepartmentDAO getInstance() {
        if (instance == null)
            instance = new DepartmentDAO();
        return instance;
    }

    // ==================== Operations ====================

    public void addDepartment(int departmentId, String name) throws Exception {
        String sql = "INSERT INTO Departments(departmentId, name) VALUES(?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            ps.setString(2, name);
            ps.executeUpdate();
        }
    }

    public void updateDepartment(int departmentId, String name) throws Exception {
        String sql = "UPDATE Departments SET name=? WHERE departmentId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, departmentId);
            ps.executeUpdate();
        }
    }

    public void deleteDepartment(int departmentId) throws Exception {
        String sql = "DELETE FROM Departments WHERE departmentId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            ps.executeUpdate();
        }
    }

    public void setDepartmentHead(int departmentId, int doctorId) throws Exception {
        String sql = "UPDATE Departments SET headDoctorId=? WHERE departmentId=?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            ps.setInt(2, departmentId);
            ps.executeUpdate();
        }
    }

    public ResultSet getAllDepartments() throws Exception {
        String sql = "SELECT * FROM Departments";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        return conn.createStatement().executeQuery(sql);
    }

    public ResultSet getDepartmentById(int departmentId) throws Exception {
        String sql = "SELECT * FROM Departments WHERE departmentId = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, departmentId);
        return ps.executeQuery();
    }
}

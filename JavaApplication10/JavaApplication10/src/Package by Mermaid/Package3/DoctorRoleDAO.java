package Package3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing Doctor Roles in the database.
 * Uses Singleton pattern and JDBC to interact with SQL Server.
 */
public class DoctorRoleDAO {

    private static DoctorRoleDAO instance;

    private DoctorRoleDAO() {}

    public static synchronized DoctorRoleDAO getInstance() {
        if (instance == null) {
            instance = new DoctorRoleDAO();
        }
        return instance;
    }

    /**
     * Assigns a role to a doctor in the DoctorRoles table.
     */
    public void assignRoleToDoctor(String doctorId, String roleName) throws SQLException {
        String sql = "INSERT INTO DoctorRoles (doctorId, roleName) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            ps.setString(2, roleName);
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all roles assigned to a specific doctor.
     */
    public ArrayList<String> getRolesForDoctor(String doctorId) throws SQLException {
        ArrayList<String> roles = new ArrayList<String>();
        String sql = "SELECT roleName FROM DoctorRoles WHERE doctorId = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getString("roleName"));
                }
            }
        }
        return roles;
    }

    /**
     * Removes a specific role from a doctor.
     */
    public void removeRoleFromDoctor(String doctorId, String roleName) throws SQLException {
        String sql = "DELETE FROM DoctorRoles WHERE doctorId = ? AND roleName = ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            ps.setString(2, roleName);
            ps.executeUpdate();
        }
    }
}

package Package3;

import Package1.Admin;
import java.sql.*;

public class AdminDAO {

    private static AdminDAO instance;

    private AdminDAO() {
    }

    public static synchronized AdminDAO getInstance() {
        if (instance == null) {
            instance = new AdminDAO();
        }
        return instance;
    }

    public void addAdmin(String adminId, String name, String phone, String email) throws Exception {
        String sql = "INSERT INTO dbo.Admins(adminId, name, phone, email) VALUES(?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, adminId);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, email);
            ps.executeUpdate();
        }
    }

    public Admin getAdminById(String adminId) throws Exception {
        String sql = "SELECT * FROM Admins WHERE adminId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, adminId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getString("adminId"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }

    public Admin login(String adminId, String name) throws Exception {
        String sql = "SELECT * FROM dbo.Admins WHERE adminId=? AND name=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, adminId);
            ps.setString(2, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getString("adminId"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }
}
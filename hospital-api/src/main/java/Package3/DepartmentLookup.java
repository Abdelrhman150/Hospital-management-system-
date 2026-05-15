package Package3;

import Package4.*;

import Package1.staff.AdministrativeDepartment;
import Package1.staff.Department;
import Package1.staff.MedicalDepartment;
import Package3.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentLookup {

    public Department getMedicalDepartmentByName(String departmentName) throws Exception {
        String sql = "SELECT * FROM Departments WHERE name = ?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MedicalDepartment(
                            rs.getString("departmentId"),
                            rs.getString("name"),
                            rs.getString("headDoctorId")
                    );
                }
            }
        }
        return null;
    }

    public Department getAdministrativeDepartmentByName(String departmentName) throws Exception {
        String sql = "SELECT * FROM AdministrativeDepartment WHERE name = ?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AdministrativeDepartment(
                            rs.getString("departmentId"),
                            rs.getString("name")
                    );
                }
            }
        }
        return null;
    }
}
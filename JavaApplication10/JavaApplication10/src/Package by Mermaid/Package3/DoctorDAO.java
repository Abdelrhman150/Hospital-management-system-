package Package3;

import java.sql.*;
<<<<<<< HEAD
import Package1.staff.Doctor;
import Package1.salary.*;
=======
import java.util.ArrayList;
import java.util.List;
import Package1.Doctor;
>>>>>>> 2d8052ceec96ad29dc964448c293e6853cad9ce4

public class DoctorDAO {

    private static DoctorDAO instance;

    private DoctorDAO() {
    }

    public static synchronized DoctorDAO getInstance() {
        if (instance == null)
            instance = new DoctorDAO();
        return instance;
    }

    public void addDoctor(String doctorId, String name, String specialization,
                          String contactEmail, double consultationFee) throws Exception {
        String sql = "INSERT INTO Doctors(doctorId, name, specialization, contactEmail, consultationFee) VALUES(?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            ps.setString(2, name);
            ps.setString(3, specialization);
            ps.setString(4, contactEmail);
            ps.setDouble(5, consultationFee);
            ps.executeUpdate();
        }
    }

    public void updateDoctor(String doctorId, String name, String specialization,
                             String contactEmail, double consultationFee) throws Exception {
        String sql = "UPDATE Doctors SET name=?, specialization=?, contactEmail=?, consultationFee=? WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, contactEmail);
            ps.setDouble(4, consultationFee);
            ps.setString(5, doctorId);
            ps.executeUpdate();
        }
    }

    public void deleteDoctor(String doctorId) throws Exception {
        String sql = "DELETE FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            ps.executeUpdate();
        }
    }

    public List<Doctor> getAllDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(mapToDoctor(rs));
            }
        }
        return doctors;
    }

    public Doctor getDoctorById(String doctorId) throws Exception {
        String sql = "SELECT * FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToDoctor(rs);
                }
            }
        }
        return null;
    }

    public List<Doctor> getAvailableDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctors WHERE AvailabilityStatus = 'Available'";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(mapToDoctor(rs));
            }
        }
        return doctors;
    }

    private Doctor mapToDoctor(ResultSet rs) throws SQLException {
        String id = rs.getString("doctorId");
        String name = rs.getString("name");
        String specialization = rs.getString("specialization");
        String phone = ""; // Might be missing in some schemas
        try {
            phone = rs.getString("phone");
        } catch (SQLException e) {
        }
        String email = "";
        try {
            email = rs.getString("email");
            if (email == null)
                email = rs.getString("contactEmail");
        } catch (SQLException e) {
            try {
                email = rs.getString("contactEmail");
            } catch (SQLException e2) {
            }
        }

        boolean availability = false;
        try {
            String status = rs.getString("AvailabilityStatus");
            availability = status != null && status.equalsIgnoreCase("Available");
        } catch (SQLException e) {
        }

        double consultationFee = 0.0;
        try {
            consultationFee = rs.getDouble("consultationFee");
        } catch (SQLException e) {
        }

        Doctor doctor = new Doctor(
                id,
                name,
                phone,
                email,
                specialization,
                availability,
                consultationFee);

        try {
            double salary = rs.getDouble("salary");
            if (!rs.wasNull()) {
                doctor.setSavedSalary(salary);
            }
        } catch (SQLException e) {
        }

        try {
            String salaryDescription = rs.getString("salaryDescription");
            doctor.setSavedSalaryDescription(salaryDescription);
        } catch (SQLException e) {
        }

        return doctor;
    }

    public String getDoctorIdByName(String doctorName) throws Exception {
        String sql = "SELECT doctorId FROM Doctors WHERE name=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("doctorId");
                }
            }
        }
        throw new Exception("Doctor not found with name: " + doctorName);
    }

    public void saveDoctorSalary(String doctorId, double salary, String salaryDescription) throws Exception {
        String sql = "UPDATE Doctors SET salary=?, salaryDescription=? WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, salary);
            ps.setString(2, salaryDescription);
            ps.setString(3, doctorId);

            if (ps.executeUpdate() == 0) {
                throw new Exception("Doctor not found with ID: " + doctorId);
            }
        }
    }

    public double getSavedSalaryByDoctorId(String doctorId) throws Exception {
        String sql = "SELECT salary FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("salary");
                }
            }
        }

        throw new Exception("No saved salary found for doctor ID: " + doctorId);
    }

    public String getSavedSalaryDescriptionByDoctorId(String doctorId) throws Exception {
        String sql = "SELECT salaryDescription FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("salaryDescription");
                }
            }
        }

        throw new Exception("No saved salary description found for doctor ID: " + doctorId);
    }
}
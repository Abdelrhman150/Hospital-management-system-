package Package3;

import java.sql.*;
import Package1.Doctor;

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

    public ResultSet getAllDoctors() throws Exception {
        String sql = "SELECT * FROM Doctors";
        Connection conn = DatabaseConnection.getConnection();
        return conn.createStatement().executeQuery(sql);
    }

    public ResultSet getDoctorById(String doctorId) throws Exception {
        String sql = "SELECT * FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctorId);
        return ps.executeQuery();
    }

    public ResultSet getAvailableDoctors() throws SQLException {
        String sql = "SELECT doctorId, name, specialization FROM Doctors WHERE AvailabilityStatus = 'Available'";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    }

    public String getDoctorIdByName(String doctorName) throws Exception {
        String sql = "SELECT doctorId FROM Doctors WHERE name=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("doctorId");
            }
        }
        throw new Exception("Doctor not found with name: " + doctorName);
    }

    public Doctor findDoctorObjectById(String doctorId) throws Exception {
        String sql = "SELECT * FROM Doctors WHERE doctorId=?";
        Connection conn = DatabaseConnection.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("doctorId");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    boolean availability = rs.getString("AvailabilityStatus").equalsIgnoreCase("Available");

                    double consultationFee = 0.0;

                    Doctor doctor = new Doctor(
                            id,
                            name,
                            phone,
                            email,
                            specialization,
                            availability,
                            consultationFee
                    );

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
            }
        }

        return null;
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
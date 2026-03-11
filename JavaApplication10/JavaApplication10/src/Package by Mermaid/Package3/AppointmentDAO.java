package Package3;

import java.sql.*;

public class AppointmentDAO {

    // ==================== Singleton ====================

    private static AppointmentDAO instance;

    private AppointmentDAO() {
    }

    public static synchronized AppointmentDAO getInstance() {
        if (instance == null)
            instance = new AppointmentDAO();
        return instance;
    }

    // ==================== Operations ====================

    public void bookAppointment(int appointmentId, int patientId, int doctorId,
            Timestamp appointmentTime, String type) throws Exception {
        String sql = "INSERT INTO Appointments(appointmentId, patientId, doctorId, appointmentTime, type, status) VALUES(?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.setInt(2, patientId);
            ps.setInt(3, doctorId);
            ps.setTimestamp(4, appointmentTime);
            ps.setString(5, type);
            ps.setString(6, "Scheduled");
            ps.executeUpdate();
        }
    }

    public void rescheduleAppointment(int appointmentId, Timestamp newTime) throws Exception {
        String sql = "UPDATE Appointments SET appointmentTime=? WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, newTime);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        }
    }

    public void cancelAppointment(int appointmentId) throws Exception {
        String sql = "UPDATE Appointments SET status='Cancelled' WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        }
    }

    public ResultSet getDoctorSchedule(int doctorId) throws Exception {
        String sql = "SELECT * FROM Appointments WHERE doctorId=? AND status != 'Cancelled' ORDER BY appointmentTime";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, doctorId);
        return ps.executeQuery();
    }

    public ResultSet getPatientAppointments(int patientId) throws Exception {
        String sql = "SELECT * FROM Appointments WHERE patientId=? ORDER BY appointmentTime";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, patientId);
        return ps.executeQuery();
    }

    public void startAppointment(int appointmentId) throws Exception {
        String sql = "UPDATE Appointments SET status='InProgress' WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        }
    }

    public void completeAppointment(int appointmentId) throws Exception {
        String sql = "UPDATE Appointments SET status='Completed' WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        }
    }
}

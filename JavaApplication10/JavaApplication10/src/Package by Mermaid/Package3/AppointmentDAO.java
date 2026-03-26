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

    public void bookAppointment(String appointmentId, String patientId, String doctorId,
            Timestamp appointmentTime, String type, String roomId, int daysOfStay) throws Exception {
        String sql = "INSERT INTO Appointments(appointmentId, patientId, doctorId, appointmentTime, type, status, roomId, daysOfStay) VALUES(?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, appointmentId);
            ps.setString(2, patientId);
            ps.setString(3, doctorId);
            ps.setTimestamp(4, appointmentTime);
            ps.setString(5, type);
            ps.setString(6, "Scheduled");
            ps.setString(7, roomId);
            ps.setInt(8, daysOfStay);
            ps.executeUpdate();
        }
    }
    

    public void GetAppointmentDetails(String appointmentId) throws Exception {
        String sql = "SELECT * FROM Appointments WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Appointment ID: " + rs.getString("appointmentId"));
                System.out.println("Patient ID: " + rs.getString("patientId"));
                System.out.println("Doctor ID: " + rs.getString("doctorId"));
                System.out.println("Appointment Time: " + rs.getTimestamp("appointmentTime"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Status: " + rs.getString("status"));
                String roomId = rs.getString("roomId");
                if (roomId != null) {
                    System.out.println("Room ID: " + roomId);
                }
                int daysOfStay = rs.getInt("daysOfStay");
                if (!rs.wasNull()) {
                    System.out.println("Days of Stay: " + daysOfStay);
                }
            } else {
                System.out.println("No appointment found with ID: " + appointmentId);
            }
        }
    }


    public void rescheduleAppointment(String appointmentId, Timestamp newTime) throws Exception {
        String sql = "UPDATE Appointments SET appointmentTime=? WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, newTime);
            ps.setString(2, appointmentId);
            ps.executeUpdate();
        }
    }

    public void cancelAppointment(String appointmentId) throws Exception {
        String sql = "UPDATE Appointments SET status='Cancelled' WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, appointmentId);
            ps.executeUpdate();
        }
    }

    public ResultSet getDoctorSchedule(String doctorId) throws Exception {
        String sql = "SELECT * FROM Appointments WHERE doctorId=? AND status != 'Cancelled' ORDER BY appointmentTime";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, doctorId);
        return ps.executeQuery();
    }

    public ResultSet getPatientAppointments(String patientId) throws Exception {
        String sql = "SELECT * FROM Appointments WHERE patientId=? ORDER BY appointmentTime";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, patientId);
        return ps.executeQuery();
    }

    public void startAppointment(String appointmentId) throws Exception {
        String sql = "UPDATE Appointments SET status='InProgress' WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, appointmentId);
            ps.executeUpdate();
        }
    }

    public void completeAppointment(String appointmentId) throws Exception {
        String sql = "UPDATE Appointments SET status='Completed' WHERE appointmentId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, appointmentId);
            ps.executeUpdate();
        }
    }
}

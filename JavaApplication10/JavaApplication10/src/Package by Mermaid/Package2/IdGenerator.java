package Package2;

import Package3.DatabaseConnection;
import java.sql.*;

public class IdGenerator {

    // ==================== Singleton ====================

    private static IdGenerator instance;

    private IdGenerator() {
    }

    public static synchronized IdGenerator getInstance() {
        if (instance == null)
            instance = new IdGenerator();
        return instance;
    }

    // ==================== Helper (private) ====================

    private int nextId(String table, String column) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT ISNULL(MAX(" + column + "), 0) + 1 FROM " + table);
            if (rs.next())
                return rs.getInt(1);
        } catch (Exception e) {
            System.err.println("Error generating ID for " + table + ": " + e.getMessage());
        }
        return 1;
    }

    // ==================== Public Methods ====================

    public int nextDoctorId() {
        return nextId("Doctors", "doctorId");
    }

    public int nextNurseId() {
        return nextId("Nurses", "nurseId");
    }

    public int nextPatientId() {
        return nextId("Patients", "patientId");
    }

    public int nextSecretaryId() {
        return nextId("Secretaries", "secretaryId");
    }

    public int nextDepartmentId() {
        return nextId("Departments", "departmentId");
    }

    public int nextAppointmentId() {
        return nextId("Appointments", "appointmentId");
    }

    public int nextRecordId() {
        return nextId("MedicalRecords", "recordId");
    }

    // ==================== Room (String ID) ====================

    public String nextRoomId() {
        String newId = "ROOM001";
        try {
            Connection conn = DatabaseConnection.getConnection();
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT MAX(roomId) AS maxId FROM rooms WHERE roomId LIKE 'ROOM%'");
            if (rs.next()) {
                String maxId = rs.getString("maxId");
                if (maxId != null && maxId.startsWith("ROOM")) {
                    int num = Integer.parseInt(maxId.substring(4));
                    newId = String.format("ROOM%03d", num + 1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error generating room ID: " + e.getMessage());
        }
        return newId;
    }
}

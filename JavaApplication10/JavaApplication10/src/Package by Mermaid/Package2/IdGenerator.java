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

    /**
     * Generates a unique role-based ID (e.g., D001, N015, SCT001)
     */
    public String generateUserIdByRole(String role) {
        String prefix = "";
        switch (role.toLowerCase()) {
            case "doctor": prefix = "D"; break;
            case "nurse": prefix = "N"; break;
            case "admin": prefix = "A"; break;
            case "patient": prefix = "P"; break;
            case "secretary": prefix = "SCT"; break;
            default: prefix = "U";
        }

        String newId = prefix + "001";
        try {
            Connection conn = DatabaseConnection.getConnection();
            // Order by length and name to find the true highest numeric ID
            String query = "SELECT username FROM Users WHERE username LIKE '" + prefix + "%' ORDER BY LEN(username) DESC, username DESC";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                
                while (rs.next()) {
                    String maxId = rs.getString("username");
                    if (maxId != null && maxId.startsWith(prefix)) {
                        String numericPart = maxId.substring(prefix.length());
                        if (numericPart.matches("\\d+")) { // Ensure it's numeric
                            int num = Integer.parseInt(numericPart);
                            newId = String.format(prefix + "%03d", num + 1);
                            break; // Found the latest numeric ID
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error generating role ID: " + e.getMessage());
        }
        return newId;
    }

    /**
     * Generates an official email based on Full Name and ID
     */
    public String generateEmail(String fullName, String generatedId) {
        // 1) convert to lowercase
        String processedName = fullName.toLowerCase();
        // 2) replace spaces with dots
        processedName = processedName.replace(" ", ".");
        // 3) remove special characters (keep dots)
        processedName = processedName.replaceAll("[^a-z0-9.]", "");
        
        // 4) append ID and @hospital.com
        return processedName + "." + generatedId.toLowerCase() + "@hospital.com";
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

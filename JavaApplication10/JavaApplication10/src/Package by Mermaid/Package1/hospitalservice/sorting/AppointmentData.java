package Package1.hospitalservice.sorting;

import java.sql.Timestamp;

public class AppointmentData {
    public String appointmentId;
    public String patientId;
    public String doctorName;
    public Timestamp appointmentTime;
    public String type;
    public String status;
    public int priority;

    public AppointmentData(String appointmentId, String patientId, String doctorName,
                          Timestamp appointmentTime, String type, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.appointmentTime = appointmentTime;
        this.type = type;
        this.status = status;
        this.priority = getPriority(status);
    }

    private int getPriority(String status) {
        if ("InProgress".equals(status)) return 1;
        if ("Scheduled".equals(status)) return 2;
        if ("Completed".equals(status)) return 3;
        return 4;
    }

    @Override
    public String toString() {
        return String.format("[%s] Patient: %s | Doctor: %s | Time: %s | Type: %s | Status: %s",
                appointmentId, patientId, doctorName, appointmentTime, type, status);
    }
}

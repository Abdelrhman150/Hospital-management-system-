package Package1.hospitalservice;

public interface Appointment {
    public void cancelAppointment(String appointmentId);
    public String getPatientId();
    public int getDaysOfStay();
    public String getAppointmentId();
    void scheduleAppointment(String patientId, String doctorName, String appointmentDate);
}

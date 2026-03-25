package Package1;

public interface Appointment {
    public void displayDetails(String appointmentId);
    public void cancelAppointment(String appointmentId);
    public String getPatientId();
    public int getDaysOfStay();
    void scheduleAppointment(String patientId, String doctorName, String appointmentDate, String roomID, Integer daysOfStay);
}
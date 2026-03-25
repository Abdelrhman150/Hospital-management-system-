package Package1;

public interface Appointment {
    public void displayDetails(int appointmentId);
    public void cancelAppointment(int appointmentId);
    public int getPatientId();
    public int getDaysOfStay();
    void scheduleAppointment(int patientId, String doctorName, String appointmentDate, Integer roomID,Integer daysOfStay);
}
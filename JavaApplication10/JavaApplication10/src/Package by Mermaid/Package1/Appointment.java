package Package1;

public interface Appointment {
    public void displayDetails() ;
    public void scheduleAppointment(int patientId, String doctorName, String appointmentDate, int roomID);
    public void cancelAppointment(int appointmentId);
}
package Package1;

public interface HospitalServicFactory {
    public Bill createBill(int patientId , int daysOfStay);
    public Appointment createAppointment(int patientId, String doctorName, String appointmentDate, int roomID, int daysOfStay);
}
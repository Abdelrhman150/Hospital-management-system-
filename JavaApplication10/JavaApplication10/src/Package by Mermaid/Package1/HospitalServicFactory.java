package Package1;

public interface HospitalServicFactory {
    public Bill createBill(String patientId , int daysOfStay);
    public Appointment createAppointment(String patientId, String doctorName, String appointmentDate);
}
package Package1.hospitalservice;

public interface HospitalServicFactory {
    public Bill createBill(String patientId , int daysOfStay);
    public Appointment createAppointment();
}

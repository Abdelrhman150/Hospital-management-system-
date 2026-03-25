package Package1;

public class OutPatientServiceFactory implements HospitalServicFactory {
    @Override
    public Bill createBill(int patientId , int daysOfStay) {
        return new VisitingBill();
    }

    @Override
    public Appointment createAppointment(int patientId, String doctorName, String appointmentDate, int roomID , int daysOfStay) {
        return new VistingAppointment();
        
    }
}
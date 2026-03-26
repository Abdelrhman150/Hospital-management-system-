package Package1;

public class OutPatientServiceFactory implements HospitalServicFactory {
    @Override
    public Bill createBill(String patientId , int daysOfStay) {
        return new VisitingBill();
    }

    @Override
    public Appointment createAppointment(String patientId, String doctorName, String appointmentDate) {
        return new VistingAppointment();
        
    }
}
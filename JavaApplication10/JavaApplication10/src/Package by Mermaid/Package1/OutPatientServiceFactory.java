package Package1;

public class OutPatientServiceFactory implements HospitalServicFactory {
    @Override
    public Bill createBill() {
        return new VisitingBill();
    }

    @Override
    public Appointment createAppointment() {
        return new VistingAppointment();
    }
}
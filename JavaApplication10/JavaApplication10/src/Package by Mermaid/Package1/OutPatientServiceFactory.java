package Package1;

public class OutPatientServiceFactory implements HospitalServicFactory {
    @Override
    public Bill createBill(String patientId, int daysOfStay) {
        VisitingBill bill = new VisitingBill();
        bill.generateBill(patientId, daysOfStay, null); // room is null for visiting
        return bill;
    }

    @Override
    public Appointment createAppointment(String patientId, String doctorName, String appointmentDate) {
        return new VistingAppointment();

    }
}
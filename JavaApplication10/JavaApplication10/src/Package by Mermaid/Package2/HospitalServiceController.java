package Package2;
import Package1.hospitalservice.*;

public class HospitalServiceController {
    HospitalServicFactory hospitalServiceFactory;

    public HospitalServiceController(HospitalServicFactory hospitalServiceFactory) {
        this.hospitalServiceFactory = hospitalServiceFactory;
    }

    public Bill CreateBill(String patientId, int daysOfStay) {
        return hospitalServiceFactory.createBill(patientId, daysOfStay);
    }

    public Appointment CreateAppointment(String patientId, String doctorName, String appointmentDate) {
        return hospitalServiceFactory.createAppointment(patientId, doctorName, appointmentDate);
    }
}

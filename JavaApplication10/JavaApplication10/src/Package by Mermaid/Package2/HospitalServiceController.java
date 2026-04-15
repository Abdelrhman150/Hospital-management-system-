package Package2;
import Package1.hospitalservice.*;

public class HospitalServiceController {
    HospitalServicFactory hospitalServiceFactory;

    public HospitalServiceController(HospitalServicFactory hospitalServiceFactory) {
        this.hospitalServiceFactory = hospitalServiceFactory;
    }

    public Bill CreateBill(String patientId, int daysOfStay) {
        return hospitalServiceFactory.createBill();
    }

    public Appointment CreateAppointment() {
        return hospitalServiceFactory.createAppointment();
    }
}

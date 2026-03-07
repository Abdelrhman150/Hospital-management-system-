package Package2;
import Package1.*;

public class HospitalServiceController {
    HospitalServicFactory hospitalServiceFactory;

    public HospitalServiceController(HospitalServicFactory hospitalServiceFactory) {
        this.hospitalServiceFactory = hospitalServiceFactory;
    }

    public Bill CreateBill() {
        return hospitalServiceFactory.createBill() ;
    }

    public Appointment CreateAppointment() {
        return hospitalServiceFactory.createAppointment();
    }
}

package Package2;
import Package1.*;

public class HospitalServiceController {
    HospitalServicFactory hospitalServiceFactory;

    public HospitalServiceController(HospitalServicFactory hospitalServiceFactory) {
        this.hospitalServiceFactory = hospitalServiceFactory;
    }

    public Bill CreateBill(int patientId, int daysOfStay) {
        return hospitalServiceFactory.createBill(patientId, daysOfStay);
    }

    public Appointment CreateAppointment(int patientId, String doctorName, String appointmentDate, int roomID) {
        return hospitalServiceFactory.createAppointment(patientId, doctorName, appointmentDate, roomID  );
    }
}

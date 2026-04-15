package Package1.hospitalservice;

import Package1.room.Room;

public class OutPatientServiceFactory implements HospitalServicFactory {
    Room room;

        public OutPatientServiceFactory(Room room) {
        this.room = room;
    }
 
    @Override
    public Bill createBill() {
        VisitingBill bill = new VisitingBill();
        return bill;
    }

    @Override
    public Appointment createAppointment() {
        return new VistingAppointment();

    }
}

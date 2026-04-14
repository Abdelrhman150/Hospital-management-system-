package Package1.hospitalservice;

import Package1.room.Room;

public class StayPatientServiceFactory implements HospitalServicFactory {
    private Room room;

    public StayPatientServiceFactory(Room room) {
        this.room = room;
    }

    @Override
    public Bill createBill(String patientId, int daysOfStay) {
        RoomBill Bill = new RoomBill(room);
        return Bill;
    }

    @Override
    public Appointment createAppointment() {
        RoomAppointment appointment = new RoomAppointment(room);
        return appointment;
    }
}

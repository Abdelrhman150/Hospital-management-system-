package Package1;

public class StayPatientServiceFactory implements HospitalServicFactory {
    private Room room;

    public StayPatientServiceFactory(Room room) {
        this.room = room;
    }

    @Override
    public Bill createBill() {
        return new RoomBill(room);
    }

    @Override
    public Appointment createAppointment() {
        RoomAppointment appointment = new RoomAppointment(room);
        return appointment;
    }
}
package Package1;

public class StayPatientServiceFactory implements HospitalServicFactory {
    private Room room;

    public StayPatientServiceFactory(Room room) {
        this.room = room;
    }

    @Override
    public Bill createBill( int patientId , int daysOfStay) {
         RoomBill Bill =  new RoomBill(room);
         Bill.generateBill(patientId, daysOfStay);
         return Bill;

    }

    @Override
    public Appointment createAppointment(int patientId, String doctorName, String appointmentDate, int roomID) {
        RoomAppointment appointment = new RoomAppointment(room);
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate, roomID);
        return appointment;
    }
}
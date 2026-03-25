package Package1;

import Package1.roomsystemfactoryflyweight.Room;
import Package3.RoomDAO;

public class StayPatientServiceFactory implements HospitalServicFactory {
    private Room room;

    public StayPatientServiceFactory(int roomID) throws Exception {

        RoomDAO roomDAO = RoomDAO.getInstance();
        this.room = roomDAO.getRoomById(roomID);
    }

    @Override
    public Bill createBill(int patientId, int daysOfStay) {
        RoomBill Bill = new RoomBill(room);
        Bill.generateBill(patientId, daysOfStay, room);
        return Bill;

    }

    @Override
    public Appointment createAppointment(int patientId, String doctorName, String appointmentDate, int roomID, int daysOfStay) {
        RoomAppointment appointment = new RoomAppointment(room);
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate, roomID, daysOfStay);
        return appointment;
    }
}
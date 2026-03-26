package Package1;

import Package1.roomsystemfactoryflyweight.Room;
import Package3.RoomDAO;

public class StayPatientServiceFactory implements HospitalServicFactory {
    private Room room;

    public StayPatientServiceFactory(String roomID) throws Exception {

        RoomDAO roomDAO = RoomDAO.getInstance();
        this.room = roomDAO.getRoomById(roomID);
    }

    @Override
    public Bill createBill(String patientId, int daysOfStay) {
        RoomBill Bill = new RoomBill(room);
        Bill.generateBill(patientId, daysOfStay, room);
        return Bill;

    }

    @Override
    public Appointment createAppointment(String patientId, String doctorName, String appointmentDate) {
        RoomAppointment appointment = new RoomAppointment(room);
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate);
        return appointment;
    }
}
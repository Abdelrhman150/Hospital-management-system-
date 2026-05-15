package Package1.hospitalservice;

import Package1.room.Room;
import Package2.HospitalServiceController;
import Package3.AppointmentDAO;
import Package3.DoctorDAO;
import Package3.RoomDAO;

public class HospitalAppointmentMediator implements AppointmentMediator {
    
    private HospitalServiceController hospitalServiceController;
    private Appointment appointment;

    @Override
    public String bookAppointment(String patientId, String doctorName, String appointmentDate, 
        String roomId, int daysOfStay, String appointmentType) throws Exception {

        Room room = null;

        if (roomId != null && !roomId.isEmpty()) {
            RoomDAO roomDAO = RoomDAO.getInstance();
            room = roomDAO.getRoomById(roomId);

            if (room == null) {
                System.out.println("Room not found.");
                return null;
            }

            if (!"Available".equals(room.getAvailabilityStatus())) {
                System.out.println("Selected room is not available. Please choose another room.");
                return null;
            }
        }

        if ("Visiting".equalsIgnoreCase(appointmentType)) {
            hospitalServiceController = new HospitalServiceController(new OutPatientServiceFactory(room));
            appointment = hospitalServiceController.CreateAppointment();

        } else if ("Stay".equalsIgnoreCase(appointmentType)) {
            hospitalServiceController = new HospitalServiceController(new StayPatientServiceFactory(room));
            appointment = hospitalServiceController.CreateAppointment();

            if (appointment instanceof RoomAppointment) {
                ((RoomAppointment) appointment).setDaysOfStay(daysOfStay);
            }

        } else {
            throw new Exception("Invalid appointment type.");
        }

        
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate);

        if ("Stay".equalsIgnoreCase(appointmentType) && room != null) {
            room.markOccupied(room.getRoomID());
            System.out.println("Room " + room.getRoomID() + " has been Booked.");
        }

        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        DoctorDAO doctorDAO = DoctorDAO.getInstance();

        try {
            String doctorId = doctorDAO.getDoctorIdByName(doctorName);

            appointmentDAO.bookAppointment(
                appointment.getAppointmentId(),
                patientId,
                doctorId,
                java.sql.Timestamp.valueOf(appointmentDate),
                appointmentType,
                roomId,
                "Stay".equalsIgnoreCase(appointmentType) ? daysOfStay : 0
            );

        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Appointment booked successfully!");
        return appointment.getAppointmentId();
    }
}
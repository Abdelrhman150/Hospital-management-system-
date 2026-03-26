package Package1;

import Package1.roomsystemfactoryflyweight.Room;
import Package1.roomsystemfactoryflyweight.RoomStatus;
import Package2.IdGenerator;
import Package3.AppointmentDAO;
import Package3.DoctorDAO;
import Package3.RoomDAO;

public class RoomAppointment implements Appointment {
    public String appointmentId;
    public String patientId;
    public String doctorName;
    public String appointmentDate;
    public int daysOfStay;
    public Room room;

    public RoomAppointment(Room room) {
        this.room = room;
    }

    @Override
    public void displayDetails(String appointmentId) {
        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        try {
            appointmentDAO.GetAppointmentDetails(appointmentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void scheduleAppointment(String patientId, String doctorName, String appointmentDate) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentId = IdGenerator.getInstance().nextAppointmentId(); ///////////////
        try {
            getExtraDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        DoctorDAO doctorDAO = DoctorDAO.getInstance();
        try {
            // Look up the doctor ID from the doctor name
            int doctorId = doctorDAO.getDoctorIdByName(doctorName);
            // Pass the doctor ID as a string to bookAppointment
            appointmentDAO.bookAppointment(this.appointmentId, this.patientId, String.valueOf(doctorId), java.sql.Timestamp.valueOf(appointmentDate), "Stay", room.getRoomID(), daysOfStay);
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void cancelAppointment(String appointmentId) {
        if (this.appointmentId != null && this.appointmentId.equals(appointmentId)) {
            System.out.println("Appointment with ID " + appointmentId + " has been cancelled.");
            // Reset appointment details
            this.patientId = null;
            this.doctorName = null;
            this.appointmentDate = null;
            this.appointmentId = null; // Resetting to indicate cancellation
        } else {
            System.out.println("No appointment found with ID: " + appointmentId);
        }
    }

    @Override
    public String getPatientId() {
        return this.patientId;

    }

    @Override
    public int getDaysOfStay() {
        return this.daysOfStay;
        }

    @Override
    public String getAppointmentId() {
        return this.appointmentId;
    }

        public void getExtraDetails() throws Exception {
             System.out.println("Enter ROOM ID: ");
        String roomID = System.console().readLine();
        if (room.getAvailabilityStatus() != "Available") {
            System.out.println("Selected room is not available. Please choose another room.");
            return;
        }
        else {
            RoomDAO roomDAO = RoomDAO.getInstance();
            this.room = roomDAO.getRoomById(roomID);
            room.markOccupied(roomID);
            System.out.println("Room " + roomID + " has been Booked.");
        }

        System.out.println("Enter Days of Stay: ");
        this.daysOfStay = Integer.parseInt(System.console().readLine());
    }
}
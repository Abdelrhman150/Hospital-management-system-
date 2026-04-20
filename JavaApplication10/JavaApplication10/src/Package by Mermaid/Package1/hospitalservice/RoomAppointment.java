package Package1.hospitalservice;

import Package1.room.Room;
import Package2.IdGenerator;

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
        // DAO logic moved to controller
    }

    @Override
    public void scheduleAppointment(String patientId, String doctorName, String appointmentDate) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentId = IdGenerator.getInstance().nextAppointmentId(); ///////////////
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

    public void CheckingRoomAvailablity() throws Exception {

        if (room.getAvailabilityStatus() != "Available") {
            System.out.println("Selected room is not available. Please choose another room.");
            return;
        } else {
            room.markOccupied(room.getRoomID());
            System.out.println("Room " + room.getRoomID() + " has been Booked.");
        }
    }

    public void setDaysOfStay(int days) {
        this.daysOfStay = days;
    }
}

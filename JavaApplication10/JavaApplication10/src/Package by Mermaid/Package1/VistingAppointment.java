package Package1;
import Package2.*;
import Package3.AppointmentDAO;

public class VistingAppointment implements Appointment {
    public int appointmentId;
    public int patientId;
    public String doctorName;
    public String appointmentDate;

    @Override
    public void displayDetails(int appointmentId) {
        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        try {
            appointmentDAO.GetAppointmentDetails(appointmentId);
        } catch (Exception e) {
            e.printStackTrace();
    }
}

    @Override
    public void scheduleAppointment(int patientId, String doctorName, String appointmentDate, Integer roomID) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentId = IdGenerator.getInstance().nextAppointmentId(); ///////////////
    }

    @Override
    public void cancelAppointment(int appointmentId) {
        if (this.appointmentId == appointmentId) {
            System.out.println("Appointment with ID " + appointmentId + " has been cancelled.");
            // Reset appointment details
            this.patientId = 0;
            this.doctorName = null;
            this.appointmentDate = null;
            this.appointmentId = 0; // Resetting to indicate cancellation
        } else {
            System.out.println("No appointment found with ID: " + appointmentId);
        }
    }
}
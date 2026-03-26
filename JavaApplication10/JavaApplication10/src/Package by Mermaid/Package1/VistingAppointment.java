package Package1;
import Package2.*;
import Package3.AppointmentDAO;
import Package3.DoctorDAO;

public class VistingAppointment implements Appointment {
    public String appointmentId;
    public String patientId;
    public String doctorName;
    public String appointmentDate;

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
        this.appointmentId = IdGenerator.getInstance().nextAppointmentId();
        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        DoctorDAO doctorDAO = DoctorDAO.getInstance();
        try {
            // Look up the doctor ID from the doctor name
            String doctorId = doctorDAO.getDoctorIdByName(doctorName);
            // Pass the doctor ID as a string to bookAppointment
            appointmentDAO.bookAppointment(this.appointmentId, this.patientId, doctorId, java.sql.Timestamp.valueOf(appointmentDate), "Visiting", null, 0);
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

    public String getAppointmentId() {
        return this.appointmentId;
    }

    @Override
    public int getDaysOfStay() {
        return 0; 
    }
}
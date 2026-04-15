package Package1.hospitalservice;
import Package2.*;


public class VistingAppointment implements Appointment {
    public String appointmentId;
    public String patientId;
    public String doctorName;
    public String appointmentDate;

    @Override
    public void displayDetails(String appointmentId) {
        // DAO logic moved to controller
    }

    @Override
    public void scheduleAppointment(String patientId, String doctorName, String appointmentDate) {
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentId = IdGenerator.getInstance().nextAppointmentId();
        // DAO logic moved to controller
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

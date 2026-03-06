package Package1;

import java.sql.Time;
import java.util.Date;

/**
 * Represents an appointment in the hospital system.
 */
public class Appointment {

    /**
     * Default constructor
     */
    public Appointment() {
    }

    public int appointmentId;
    public Date appointmentDate;
    public Time scheduledTime;
    public String reasonForVisit;
    public AppointmentStatus appointmentStatus;
    public AppointmentType appointmentType;
    public int patientId;
    public int doctorId;

    /**
     * Cancel the appointment.
     */
    public void cancel() {
        // TODO implement here
    }

    /**
     * Mark the appointment as in progress.
     */
    public void markInProgress() {
        // TODO implement here
    }

    /**
     * Complete the appointment.
     */
    public void complete() {
        // TODO implement here
    }

    /**
     * @param newDate 
     * @param newTime
     */
    public void reschedule(Date newDate, Time newTime) {
        // TODO implement here
    }
}
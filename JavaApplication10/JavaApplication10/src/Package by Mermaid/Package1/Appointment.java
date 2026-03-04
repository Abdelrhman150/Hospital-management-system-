package Package1;

import java.sql.Time;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Appointment {

    /**
     * Default constructor
     */
    public Appointment() {
    }

    /**
     * 
     */
    public int appointmentId;

    /**
     * 
     */
    public Date appointmentDate;

    /**
     * 
     */
    public Time scheduledTime;

    /**
     * 
     */
    public String reasonForVisit;

    /**
     * 
     */
    public AppointmentStatus appointmentStatus;

    /**
     * 
     */
    public AppointmentType appointmentType;

    /**
     * 
     */
    public int patientId;

    /**
     * 
     */
    public int doctorId;





    /**
     * 
     */
    public void cancel() {
        // TODO implement here
    }

    /**
     * 
     */
    public void markInProgress() {
        // TODO implement here
    }

    /**
     * 
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
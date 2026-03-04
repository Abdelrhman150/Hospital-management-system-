package Package1;

import java.sql.Time;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Patient {

    /**
     * Default constructor
     */
    public Patient() {
    }

    /**
     * 
     */
    public int patientId;

    /**
     * 
     */
    public String name;

    /**
     * 
     */
    public Date dateOfBirth;

    /**
     * 
     */
    public Gender gender;

    /**
     * 
     */
    public String contactNumber;

    /**
     * 
     */
    public String emergencyContact;

    /**
     * 
     */
    public String bloodType;

    /**
     * 
     */
    public String insuranceProvider;



    /**
     * @param onDate
     */
    public void getAge(Date onDate) {
        // TODO implement here
    }

    /**
     * @param newContact 
     * @param newEmergency
     */
    public void updateContact(Object newContact, Object newEmergency) {
        // TODO implement here
    }

    /**
     * 
     */
    public class Doctor {

        /**
         * Default constructor
         */
        public Doctor() {
        }

        /**
         * 
         */
        public int doctorId;

        /**
         * 
         */
        public String name;

        /**
         * 
         */
        public String specialization;

        /**
         * 
         */
        public String contactEmail;

        /**
         * 
         */
        public double consultationFee;

        /**
         * 
         */
        public String availabilitySchedule;



        /**
         * @param date 
         * @param time
         */
        public void isAvailable(Date date, Time time) {
            // TODO implement here
        }

    }

}
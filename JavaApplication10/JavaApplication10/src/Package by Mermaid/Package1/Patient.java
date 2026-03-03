package Package1;

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
    public string name;

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
    public string contactNumber;

    /**
     * 
     */
    public string emergencyContact;

    /**
     * 
     */
    public string bloodType;

    /**
     * 
     */
    public string insuranceProvider;



    /**
     * @param onDate
     */
    public void getAge(void onDate) {
        // TODO implement here
    }

    /**
     * @param newContact 
     * @param newEmergency
     */
    public void updateContact(void newContact, void newEmergency) {
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
        public string name;

        /**
         * 
         */
        public string specialization;

        /**
         * 
         */
        public string contactEmail;

        /**
         * 
         */
        public decimal consultationFee;

        /**
         * 
         */
        public string availabilitySchedule;



        /**
         * @param date 
         * @param time
         */
        public void isAvailable(void date, void time) {
            // TODO implement here
        }

    }

}
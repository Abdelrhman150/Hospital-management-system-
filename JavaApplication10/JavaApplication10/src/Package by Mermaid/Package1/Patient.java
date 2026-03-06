package Package1;

import java.util.Date;

/**
 * Represents a patient in the hospital system.
 */
public class Patient {

    /**
     * Default constructor
     */
    public Patient() {
    }

    public int patientId;
    public String name;
    public Date dateOfBirth;
    public Gender gender;
    public String contactNumber;
    public String emergencyContact;
    public String bloodType;
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
}
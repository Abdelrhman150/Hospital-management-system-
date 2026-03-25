package Package1;

import java.util.Date;

/**
 * Represents a nurse in the hospital system.
 */
public class Nurse {

    /**
     * Default constructor
     */
    public Nurse() {
    }

    public String nurseId;
    public String fullName;
    public ShiftType assignedShift;
    public String contactNumber;
    public boolean isAvailable;

    /**
     * @param isAvailable
     */
    public void setAvailability(boolean isAvailable) {
        // TODO implement here
    }

    /**
     * @param newShift
     */
    public void changeShift(ShiftType newShift) {
        // TODO implement here
    }
}
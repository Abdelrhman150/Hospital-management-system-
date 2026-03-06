package Package1;

import java.util.Date;

/**
 * Represents a medical record.
 */
public class MedicalRecord {

    /**
     * Default constructor
     */
    public MedicalRecord() {
    }

    public int recordId;
    public Date dateCreated;
    public String chiefComplaint;
    public String clinicalDiagnosis;
    public int patientId;
    public int doctorId;
    public int appointmentId;

    /**
     * @param newDiagnosis
     */
    public void updateDiagnosis(String newDiagnosis) {
        // TODO implement here
    }

    /**
     * @param text
     */
    public void addComplaint(String text) {
        // TODO implement here
    }
}
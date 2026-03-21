package Package1;

import java.util.Date;

/**
 * Represents a medical record.
 */
public class MedicalRecord implements MedicalRecordInterface {

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

    @Override
    public String getDetails() {
        return "=== Medical Record ==="
             + "\nRecord ID     : " + recordId
             + "\nPatient ID    : " + patientId
             + "\nDoctor ID     : " + doctorId
             + "\nDate Created  : " + dateCreated
             + "\nComplaint     : " + chiefComplaint
             + "\nDiagnosis     : " + clinicalDiagnosis;
    }

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
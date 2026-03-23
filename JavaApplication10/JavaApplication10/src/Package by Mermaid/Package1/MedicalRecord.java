package Package1;

import java.util.Date;

/**
 * Pure data model for a medical record.
 * Business logic is in MedicalRecordController (Package2).
 */
public class MedicalRecord implements MedicalRecordInterface {

    public int recordId;
    public Date dateCreated;
    public String chiefComplaint;
    public String clinicalDiagnosis;
    public int patientId;
    public int doctorId;
    public int appointmentId;

    public MedicalRecord() {}

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
}
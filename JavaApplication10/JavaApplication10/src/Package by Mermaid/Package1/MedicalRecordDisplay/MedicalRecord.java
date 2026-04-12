package Package1.MedicalRecordDisplay;

import java.util.Date;

/**
 * Pure data model for a medical record.
 * Business logic is in MedicalRecordController (Package2).
 */
public class MedicalRecord implements MedicalRecordInterface {

    public String recordId;
    public Date dateCreated;
    public String chiefComplaint;
    public String clinicalDiagnosis;
    public String patientId;
    public String doctorId;
    public String appointmentId;

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
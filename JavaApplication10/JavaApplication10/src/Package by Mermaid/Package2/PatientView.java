package Package2;

import Package1.MedicalRecord;

public class PatientView extends RecordView {

    public PatientView(DisplayPlatform platform) {
        super(platform);
    }

    @Override
    public void display(MedicalRecord record) {
        String content = "[ Your Medical Record Summary ]\n" +
                "Date     : " + record.dateCreated + "\n" +
                "Complaint: " + record.chiefComplaint + "\n" +
                "Diagnosis: " + record.clinicalDiagnosis;

        platform.display(content);
    } 
}

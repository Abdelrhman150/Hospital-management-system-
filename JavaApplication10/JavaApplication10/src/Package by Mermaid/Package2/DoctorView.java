package Package2;

import Package1.MedicalRecord;

public class DoctorView extends RecordView {

    public DoctorView(DisplayPlatform platform) {
        super(platform);
    }

    @Override
    public void display(MedicalRecord record) {
        String content = "[ Doctor's Full Medical Record ]\n" +
                "Record ID      : " + record.recordId + "\n" +
                "Patient ID     : " + record.patientId + "\n" +
                "Doctor ID      : " + record.doctorId + "\n" +
                "Appointment ID : " + record.appointmentId + "\n" +
                "Date Created   : " + record.dateCreated + "\n" +
                "Chief Complaint: " + record.chiefComplaint + "\n" +
                "Diagnosis      : " + record.clinicalDiagnosis;

        platform.display(content);
    }
}

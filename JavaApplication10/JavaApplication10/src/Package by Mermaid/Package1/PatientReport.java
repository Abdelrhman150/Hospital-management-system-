package Package1;

import Package2.ReportFormatter;

/**
 * Refined Abstraction: Patient-specific report.
 * This class uses real-time data from SQL Server to present patient information.
 */
public class PatientReport extends Report {
    private String patientName;
    private String diagnosis;

    public PatientReport(String patientName, String diagnosis, ReportFormatter formatter) {
        super(formatter);
        this.patientName = patientName;
        this.diagnosis = diagnosis;
    }

    @Override
    public void generate() {
        formatter.formatHeader("Patient Medical History Export");
        formatter.formatBody("Patient Name: " + patientName + "\nDiagnosis: " + diagnosis);
        formatter.formatFooter("Medical Confidentiality Apply | System Generated History.");
    }
}

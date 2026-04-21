package Package1.report;

/**
 * Refined Abstraction: Patient-specific report.
 * - Implements Template Method steps (drawHeader, insertMainData, etc.)
 * - Uses the Bridge (formatter) to format each section.
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
    protected void drawHeader() {
        formatter.formatHeader("Patient Medical History Export");
    }

    @Override
    protected void insertMainData() {
        formatter.formatBody("Patient Name: " + patientName);
    }

    @Override
    protected void addDetails() {
        formatter.formatBody("Diagnosis: " + diagnosis);
    }

    @Override
    protected void finalSignature() {
        formatter.formatFooter("Medical Confidentiality Apply | System Generated History.");
    }
}

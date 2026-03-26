package Package2;

import Package1.AllergyWarningsDecorator;
import Package1.LabTestResultsDecorator;
import Package1.MedicalRecord;
import Package1.MedicalRecordInterface;
import Package1.XRayScansDecorator;

/**
 * Controller for medical record operations.
 *
 * يستخدم:
 *  - MedicalRecord          → البيانات الأساسية (model)
 *  - MedicalRecordInterface → الـ type المشترك بين السجل والديكوريتورز
 *  - MedicalRecordDecorator → الأساس، عبر الديكوريتورز الـ3
 */
public class MedicalRecordController {

    private MedicalRecordInterface record; // يبدأ كـ MedicalRecord، وممكن يُزيَّن

    // ── Constructor ────────────────────────────────────────────────────────────

    public MedicalRecordController(MedicalRecord medicalRecord) {
        this.record = medicalRecord; // MedicalRecord implements MedicalRecordInterface
    }

    // ── Decorator Methods ──────────────────────────────────────────────────────

    public void addLabResults(String labResults) {
        record = new LabTestResultsDecorator(record, labResults);
    }

    public void addXRayScan(String scanDetails) {
        record = new XRayScansDecorator(record, scanDetails);
    }

    public void addAllergyWarning(String warning) {
        record = new AllergyWarningsDecorator(record, warning);
    }

    // ── Display ────────────────────────────────────────────────────────────────

    public String getFullDetails() {
        return record.getDetails();
    }
}
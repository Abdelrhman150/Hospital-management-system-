package Package1;

public class LabTestResultsDecorator extends MedicalRecordDecorator {

    private String labResults;

    public LabTestResultsDecorator(MedicalRecordInterface record, String labResults) {
        super(record);
        this.labResults = labResults;
    }

    @Override
    public String getDetails() {
        return wrappedRecord.getDetails() +
                "\n--- Lab Test Results ---" +
                "\n" + labResults;
    }
}

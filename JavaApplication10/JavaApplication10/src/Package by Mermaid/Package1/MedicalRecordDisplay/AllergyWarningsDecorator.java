package Package1.MedicalRecordDisplay;

public class AllergyWarningsDecorator extends MedicalRecordDecorator {

    private String allergyWarning;

    public AllergyWarningsDecorator(MedicalRecordInterface record, String allergyWarning) {
        super(record);
        this.allergyWarning = allergyWarning;
    }

    @Override
    public String getDetails() {
        return wrappedRecord.getDetails() +
                "\n⚠ Allergy Warning ---" +
                "\n" + allergyWarning;
    }
}

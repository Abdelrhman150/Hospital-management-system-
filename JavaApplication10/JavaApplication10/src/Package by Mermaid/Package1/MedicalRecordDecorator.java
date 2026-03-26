package Package1;

public abstract class MedicalRecordDecorator implements MedicalRecordInterface {

    protected MedicalRecordInterface wrappedRecord;

    public MedicalRecordDecorator(MedicalRecordInterface record) {
        this.wrappedRecord = record;
    }

    @Override
    public String getDetails() {
        return wrappedRecord.getDetails();
    }
}

package Package1;

public class XRayScansDecorator extends MedicalRecordDecorator {

    private String scanDetails;

    public XRayScansDecorator(MedicalRecordInterface record, String scanDetails) {
        super(record);
        this.scanDetails = scanDetails;
    }

    @Override
    public String getDetails() {
        return wrappedRecord.getDetails() +
                "\n--- X-Ray Scans ---" +
                "\n" + scanDetails;
    }
}

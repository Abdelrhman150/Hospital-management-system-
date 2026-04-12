package Package1.MedicalRecordDisplay;

public abstract class RecordView {

    protected DisplayPlatform platform;

    public RecordView(DisplayPlatform platform) {
        this.platform = platform;
    }

    public abstract void display(MedicalRecord record);
}

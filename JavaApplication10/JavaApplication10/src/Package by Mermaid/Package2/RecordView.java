package Package2;

import Package1.MedicalRecord;

public abstract class RecordView {

    protected DisplayPlatform platform;

    public RecordView(DisplayPlatform platform) {
        this.platform = platform;
    }

    public abstract void display(MedicalRecord record);
}

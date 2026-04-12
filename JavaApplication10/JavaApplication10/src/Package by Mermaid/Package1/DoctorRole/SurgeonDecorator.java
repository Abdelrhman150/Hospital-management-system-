package Package1.DoctorRole;

/**
 * Decorator that adds Surgeon responsibilities to a doctor.
 */
public class SurgeonDecorator extends DoctorDecorator {

    public SurgeonDecorator(DoctorService doctor) {
        super(doctor);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Surgeon Role";
    }

    @Override
    public void performDuties() {
        super.performDuties();
        System.out.println("Surgeon Duties: Performing surgeries, handling critical cases.");
    }
}

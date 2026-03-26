package Package1;

/**
 * Decorator that adds On-call responsibilities to a doctor.
 */
public class OnCallDoctorDecorator extends DoctorDecorator {

    public OnCallDoctorDecorator(DoctorService doctor) {
        super(doctor);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + On-call Doctor Role";
    }

    @Override
    public void performDuties() {
        super.performDuties();
        System.out.println("Emergency Duties: Emergency response, night shifts.");
    }
}

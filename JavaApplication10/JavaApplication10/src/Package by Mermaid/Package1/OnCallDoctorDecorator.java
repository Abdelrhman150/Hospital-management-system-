package Package1;

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

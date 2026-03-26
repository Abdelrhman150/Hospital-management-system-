package Package1;

/**
 * Basic Doctor implementation representing a normal doctor.
 * Part of the Decorator Pattern implementation.
 */
public class BasicDoctor implements DoctorService {
    private String name;
    private String specialization;

    public BasicDoctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    @Override
    public String getDescription() {
        return "Normal Doctor (" + name + " - " + specialization + ")";
    }

    @Override
    public void performDuties() {
        System.out.println("Basic Duties: Examining patients, prescribing medicine.");
    }
}

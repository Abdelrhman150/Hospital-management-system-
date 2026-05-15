package Package1.DoctorRole;

/**
 * Decorator that adds Head of Department responsibilities to a doctor.
 */
public class HeadOfDepartmentDecorator extends DoctorDecorator {

    public HeadOfDepartmentDecorator(DoctorService doctor) {
        super(doctor);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Head of Department Role";
    }

    @Override
    public void performDuties() {
        super.performDuties();
        System.out.println("Administrative Duties: Approving leaves, managing department staff.");
    }
}

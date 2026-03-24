package Package1;

/**
 * Abstract Decorator class for Doctor Service.
 * Part of the Decorator Pattern implementation.
 */
public abstract class DoctorDecorator implements DoctorService {
    protected DoctorService decoratedDoctor;

    public DoctorDecorator(DoctorService doctor) {
        this.decoratedDoctor = doctor;
    }

    @Override
    public String getDescription() {
        return decoratedDoctor.getDescription();
    }

    @Override
    public void performDuties() {
        decoratedDoctor.performDuties();
    }
}

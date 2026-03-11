package Package1;
import Package2.*;

public class Doctor extends User {
    private String specialization;
    private boolean availability;

    public Doctor( String name, String phone, String email, String specialization, boolean availability) {
        super( name, phone, email);
        this.id = IdGenerator.getInstance().nextDoctorId();
        this.specialization = specialization;
        this.availability = availability;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void diagnosePatient() {
        System.out.println(name + " is diagnosing a patient.");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
        System.out.println("Available: " + availability);
    }
}
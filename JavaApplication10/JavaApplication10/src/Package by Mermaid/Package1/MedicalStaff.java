package Package1;
import Package2.*;

public class MedicalStaff implements StaffFactory {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String specialization;
    private boolean availability;
    private Department department;

    public MedicalStaff( String name, String phone, String email,
            String specialization, boolean availability, Department department) {
        this.id = IdGenerator.getInstance().nextDoctorId();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
        this.availability = availability;
        this.department = department;
    }

    public MedicalStaff( int ID ,String name, String phone, String email,
                String specialization, boolean availability, Department department) {
            this.id = ID;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.specialization = specialization;
            this.availability = availability;
            this.department = department;
        }


    @Override
    public User createUser() {
        return new Doctor(id ,name, phone, email, specialization, availability);
    }

    @Override
    public Department createDepartment() {
        return department;
    }
}
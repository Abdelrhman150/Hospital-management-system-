package Package1;

public class MedicalStaff implements StaffFactory {

    private String name;
    private String phone;
    private String email;
    private String specialization;
    private boolean availability;
    private Department department;

    public MedicalStaff( String name, String phone, String email,
            String specialization, boolean availability, Department department) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
        this.availability = availability;
        this.department = department;
    }

    @Override
    public User createUser() {
        return new Doctor(name, phone, email, specialization, availability);
    }

    @Override
    public Department createDepartment() {
        return department;
    }
}
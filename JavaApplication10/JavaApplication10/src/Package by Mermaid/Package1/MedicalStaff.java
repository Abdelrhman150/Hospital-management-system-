package Package1;

public class MedicalStaff implements StaffFactory {

    private int doctorId;
    private String name;
    private String phone;
    private String email;
    private String specialization;
    private Department department;

    public MedicalStaff(int doctorId, String name, String phone, String email,
            String specialization, Department department) {
        this.doctorId = doctorId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
        this.department = department;
    }

    @Override
    public User createUser() {
        return new Doctor(doctorId, name, phone, email, specialization);
    }

    @Override
    public Department createDepartment() {
        return department;
    }
}
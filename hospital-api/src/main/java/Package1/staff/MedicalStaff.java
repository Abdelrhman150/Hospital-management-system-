package Package1.staff;

import Package2.*;

public class MedicalStaff implements StaffFactory {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String specialization;
    private Department department;

    public MedicalStaff(String name, String phone, String email,
                        String specialization, Department department) {
        this.id = IdGenerator.getInstance().nextDoctorId();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
        this.department = department;
    }

    public MedicalStaff(String name2, String phone2, String email2, String specialization2, boolean availability,
            double consultationFee, Department department2) {
        //TODO Auto-generated constructor stub
    }

    @Override
    public User createUser() {
        return new Doctor(id, name, phone, email, specialization, department.getId());
    }

    @Override
    public Department createDepartment() {
        return department;
    }
}
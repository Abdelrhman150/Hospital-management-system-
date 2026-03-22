package Package1;

import Package2.*;

public class AdministrativeStaff implements StaffFactory {

    private int staffId;
    private String name;
    private String phone;
    private String email;
    private String position;
    private Department department;

    public AdministrativeStaff(String name, String phone, String email,
            String position, Department department) {
        this.staffId = IdGenerator.getInstance().nextSecretaryId();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.department = department;
    }

    public AdministrativeStaff(int staffId, String name, String phone, String email,
            String position, Department department) {
        this.staffId = staffId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.department = department;
    }

    @Override
    public User createUser() {
        return new Secretary(staffId, name, phone, email, position);
    }

    @Override
    public Department createDepartment() {
        return department;
    }
}

package Package1;

public class MedicalDepartment extends Department {

    public MedicalDepartment(int departmentId, String departmentName, int floorNumber, int departmentHeadId) {
        super(departmentId, departmentName, floorNumber, departmentHeadId);
    }

    @Override
    public String getDepartmentType() {
        return "Medical";
    }

    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public void displayDepartment() {
        super.displayDepartment();
    }
}

package Package1;

public class MedicalDepartment extends Department {

    public MedicalDepartment(String departmentId, String departmentName, int floorNumber, String departmentHeadId) {
        super(departmentId, departmentName, floorNumber, departmentHeadId);
    }

    @Override
    public String getDepartmentType() {
        return "Medical";
    }

    @Override
    public void displayDepartment() {
        super.displayDepartment();
    }
}

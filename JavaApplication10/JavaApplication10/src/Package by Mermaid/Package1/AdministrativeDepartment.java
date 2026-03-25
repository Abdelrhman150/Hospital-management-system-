package Package1;

public class AdministrativeDepartment extends Department {

    public AdministrativeDepartment(String departmentId, String departmentName, int floorNumber, String departmentHeadId) {
        super(departmentId, departmentName, floorNumber, departmentHeadId);
    }

    @Override
    public String getDepartmentType() {
        return "Administrative";
    }

    @Override
    public void displayDepartment() {
        super.displayDepartment();
    }

}
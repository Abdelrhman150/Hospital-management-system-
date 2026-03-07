package Package1;

public class AdministrativeDepartment extends Department {

    public AdministrativeDepartment(int departmentId, String departmentName, int floorNumber, int departmentHeadId) {
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
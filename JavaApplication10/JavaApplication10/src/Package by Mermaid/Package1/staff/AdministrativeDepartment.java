package Package1.staff;

public class AdministrativeDepartment extends Department {

    public AdministrativeDepartment(String departmentId, String departmentName) {
        super(departmentId, departmentName);
    }

    @Override
    public String getDepartmentType() {
        return "Administrative";
    }

    @Override
    public void displayDepartment() {
        super.displayDepartment();
    }

    @Override
    protected String getId() {
        return departmentId;
    }
}
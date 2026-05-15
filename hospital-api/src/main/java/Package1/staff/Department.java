package Package1.staff;

public abstract class Department {
    protected String departmentId;
    protected String departmentName;

    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public abstract String getDepartmentType();

    public void displayDepartment() {
        System.out.println("Department ID: " + departmentId);
        System.out.println("Department Name: " + departmentName);
        System.out.println("Department Type: " + getDepartmentType());
    }

    protected abstract String getId();
}
package Package1.staff;

public class MedicalDepartment extends Department {
    private String departmentHeadId;

    public MedicalDepartment(String departmentId, String departmentName, String departmentHeadId) {
        super(departmentId, departmentName);
        this.departmentHeadId = departmentHeadId;
    }

 
    public String getDepartmentHeadId() {
        return departmentHeadId;
    }

    @Override
    public String getDepartmentType() {
        return "Medical";
    }

    @Override
    public void displayDepartment() {
        super.displayDepartment();
        System.out.println("Department Head ID: " + departmentHeadId);
    }

    @Override
    protected String getId() {
        return departmentId;
    }
}
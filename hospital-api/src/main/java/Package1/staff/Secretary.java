package Package1.staff;

public class Secretary extends User {
    private String shift;
    private String departmentId;

    public Secretary(String id, String name, String phone, String email,
                     String shift, String departmentId) {
        super(id, name, phone, email);
        this.shift = shift;
        this.departmentId = departmentId;
    }

    @Override
    public String getRole() {
        return "Secretary";
    }

    public String getShift() {
        return shift;
    }


    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Shift: " + shift);
        System.out.println("Department ID: " + departmentId);
    }
}